package android.developer.project.ui.main.fragment.list

import android.developer.project.base.BaseViewModel
import android.developer.project.data.DataState
import android.developer.project.data.model.ui.Repository
import android.developer.project.data.model.ui.Sort
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
class ListViewModel@ViewModelInject constructor(
    private val githubRepository: GithubRepository,
    @Assisted private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val STATE_OVERVIEW_DATA = "STATE_OVERVIEW_DATA"

    private var allRepositories = mutableListOf<Repository>()
    private var searchedRepositories = mutableListOf<Repository>()

    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> = _repositories

    val searchText = MutableLiveData("Android")

    val selectedSort = MutableLiveData(Sort.NONE)

    fun loadRepositories() {
        viewModelScope.launch {
            githubRepository.getRepositories(searchText.value ?: "").onEach { dataState ->
                when(dataState) {
                    is DataState.Success<List<Repository>?> -> {
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

    private fun handleData(repositories: List<Repository>?) {
        allRepositories.clear()
        allRepositories.addAll(repositories?.map { it.copy() } ?: emptyList())
        searchedRepositories.clear()
        searchedRepositories.addAll(repositories?.map { it.copy() } ?: emptyList())
        sortList()
    }

    fun sortList() {
        searchedRepositories = when(selectedSort.value) {
            Sort.WatchersASC -> searchedRepositories.sortedBy { it.watchers }.toMutableList()
            Sort.WatchersDESC -> searchedRepositories.sortedByDescending { it.watchers }.toMutableList()
            Sort.ForksASC -> searchedRepositories.sortedBy { it.forks }.toMutableList()
            Sort.ForksDESC -> searchedRepositories.sortedByDescending { it.forks }.toMutableList()
            Sort.IssuesASC -> searchedRepositories.sortedBy { it.issues }.toMutableList()
            Sort.IssuesDESC -> searchedRepositories.sortedByDescending { it.issues }.toMutableList()
            Sort.RepositoryASC -> searchedRepositories.sortedBy { it.repositoryName }.toMutableList()
            Sort.RepositoryDESC -> searchedRepositories.sortedByDescending { it.repositoryName }.toMutableList()
            else -> allRepositories
        }
        _repositories.postValue(searchedRepositories)
    }
}