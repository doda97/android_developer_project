package android.developer.project.ui.login.fragment

import android.developer.project.R
import android.developer.project.base.BaseViewModel
import android.developer.project.data.DataState
import android.developer.project.data.model.rest.LoginModel
import android.developer.project.data.model.ui.MessageDialogModel
import android.developer.project.data.repository.UserRepository
import android.developer.project.ui.utils.SingleEvent
import android.developer.project.ui.utils.SingleLiveEvent
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
class LoginFragmentViewModel
@ViewModelInject constructor(
    private val userRepository: UserRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val userLogin = SingleLiveEvent<Unit>()

    val username: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")

    fun login(){
        viewModelScope.launch {
            userRepository.userLogin(username.value!!, password.value!!).onEach { dataState ->
                when(dataState){
                    is DataState.Success<Boolean> -> {
                        showProgress(false)
                        userLogin.call()
                    }
                    is DataState.Error -> {
                        showProgress(false)
                        if(dataState.statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                            globalDialogMessage.value = SingleEvent(MessageDialogModel.default(R.string.login_failed))
                        } else {
                            handleError(dataState.exception)
                        }
                    }
                    is DataState.Loading -> {
                        showProgress(true)
                    }
                }
            }
            .launchIn(viewModelScope)
        }
    }
}