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
import org.threeten.bp.format.DateTimeFormatter

@ExperimentalCoroutinesApi
class RepositoryViewModel@ViewModelInject constructor(
    private val githubRepository: GithubRepository,
    @Assisted private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val dateFormat = "dd.MM.yyyy"

    private val STATE_OVERVIEW_DATA = "STATE_OVERVIEW_DATA"

    private val _repository = MutableLiveData<Repository>()
    val repository: LiveData<Repository> = _repository

    val avatarUrl = MutableLiveData("")
    val userName = MutableLiveData("")
    val repositoryName = MutableLiveData("")
    val defaultBranch = MutableLiveData("")
    val dateOfCreation = MutableLiveData("")
    val dateOfUpdate = MutableLiveData("")
    val userType = MutableLiveData("")

    fun loadRepository(username: String, repositoryName: String) {
        viewModelScope.launch {
            githubRepository.getRepository(username, repositoryName).onEach { dataState ->
                when(dataState) {
                    is DataState.Success<Repository?> -> {
                        showProgress(false)
                        handleData(dataState.data)
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

    private fun handleData(repository: Repository?) {
        this._repository.postValue(repository)
        avatarUrl.postValue(repository?.authorImage)
        userName.postValue(repository?.authorName)
        repositoryName.postValue(repository?.repositoryName)
        defaultBranch.postValue("Default branch: " + repository?.defaultBranch)
        dateOfCreation.postValue("Created at: " + repository?.createdAt?.format(DateTimeFormatter.ofPattern(dateFormat)))
        dateOfUpdate.postValue("Updated at: " + repository?.updatedAt?.format(DateTimeFormatter.ofPattern(dateFormat)))
        userType.postValue("User type: " + repository?.userType)
    }

    fun getUserUrl(): String? {
        return repository.value?.userUrl
    }

    fun getRepositoryUrl(): String? {
        return repository.value?.repositoryUrl
    }
}