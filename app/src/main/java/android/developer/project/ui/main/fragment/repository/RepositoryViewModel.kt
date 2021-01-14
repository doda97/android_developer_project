package android.developer.project.ui.main.fragment.repository

import android.developer.project.base.BaseViewModel
import android.developer.project.data.DataState
import android.developer.project.data.model.ui.Repository
import android.developer.project.data.repository.GithubRepository
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

@ExperimentalCoroutinesApi
class RepositoryViewModel@ViewModelInject constructor(
    private val githubRepository: GithubRepository,
    @Assisted private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val STATE_OVERVIEW_DATA = "STATE_OVERVIEW_DATA"

    private val _repository = MutableLiveData<Repository>()
    val repository: LiveData<Repository> = _repository

    fun loadRepository(id: Long) {
        viewModelScope.launch {
            githubRepository.getRepositories().onEach { dataState ->
                when(dataState) {
                    is DataState.Success<List<Repository>?> -> {
                        showProgress(false)
                        handleData(dataState.data?.find { it.id == id }!!)
                        savedStateHandle.set(STATE_OVERVIEW_DATA, true)
                    }
                    is DataState.Error -> {
                        showProgress(false)
                        if(savedStateHandle.get<Boolean>(STATE_OVERVIEW_DATA) != true) {
                            handleError(dataState.exception)
                        }
                    }
                    is DataState.Loading -> {
                        if(savedStateHandle.get<Boolean>(STATE_OVERVIEW_DATA) != true) {
                            showProgress(true)
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun handleData(repository: Repository) {
        this._repository.postValue(repository)
    }
}