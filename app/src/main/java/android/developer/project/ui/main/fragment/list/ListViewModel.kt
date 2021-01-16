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
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*

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

    val searchText = MutableLiveData("a")

    val selectedSort = MutableLiveData(Sort.NONE)

    var job : Job? = null

    fun loadRepositories() {
        job?.cancel()

        job = viewModelScope.launch {
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

    private fun sortList() {
        searchedRepositories = when(selectedSort.value) {
            Sort.WatchersASC -> allRepositories.map { it.copy() }.sortedBy{ it.watchers }.toMutableList()
            Sort.WatchersDESC -> allRepositories.map { it.copy() }.sortedByDescending { it.watchers }.toMutableList()
            Sort.ForksASC -> allRepositories.map { it.copy() }.sortedBy { it.forks }.toMutableList()
            Sort.ForksDESC -> allRepositories.map { it.copy() }.sortedByDescending { it.forks }.toMutableList()
            Sort.IssuesASC -> allRepositories.map { it.copy() }.sortedBy { it.issues }.toMutableList()
            Sort.IssuesDESC -> allRepositories.map { it.copy() }.sortedByDescending { it.issues }.toMutableList()
            Sort.RepositoryASC -> allRepositories.map { it.copy() }.sortedBy { it.repositoryName.toLowerCase(Locale.getDefault()) }.toMutableList()
            Sort.RepositoryDESC -> allRepositories.map { it.copy() }.sortedByDescending { it.repositoryName.toLowerCase(Locale.getDefault()) }.toMutableList()
            else -> allRepositories.map { it.copy() }.toMutableList()
        }
        _repositories.value = searchedRepositories
    }
}