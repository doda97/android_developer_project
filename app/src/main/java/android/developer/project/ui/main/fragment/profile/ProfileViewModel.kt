package android.developer.project.ui.main.fragment.profile

import android.developer.project.R
import android.developer.project.base.BaseViewModel
import android.developer.project.data.DataState
import android.developer.project.data.model.ui.MessageDialogModel
import android.developer.project.data.model.ui.Owner
import android.developer.project.data.model.ui.Repository
import android.developer.project.data.repository.UserRepository
import android.developer.project.ui.utils.SingleEvent
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
class ProfileViewModel@ViewModelInject constructor(
    private val userRepository: UserRepository,
    @Assisted private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val STATE_OVERVIEW_DATA = "STATE_OVERVIEW_DATA"

    private val _user = MutableLiveData<Owner>()
    val user: LiveData<Owner> = _user

    val userName = MutableLiveData("")
    val avatarUrl = MutableLiveData("")
    val githubAccount = MutableLiveData("")

    fun loadProfile(name: String) {
        viewModelScope.launch {
            userRepository.getGitUser(name).onEach { dataState ->
                when(dataState){
                    is DataState.Success<Owner> -> {
                        showProgress(false)
                        handleData(dataState.data)
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

    private fun handleData(user: Owner) {
        _user.postValue(user)
        userName.postValue(user.name)
        avatarUrl.postValue(user.avatarUrl)
        githubAccount.postValue(user.userUrl)
    }

    fun logout() {
        userRepository.userLogout()
    }

    fun getGithubUrl(): String? {
        return githubAccount.value
    }
}