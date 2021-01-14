package android.developer.project.ui.main.fragment.repository

import android.developer.project.base.BaseViewModel
import android.developer.project.data.DataState
import android.developer.project.data.repository.GithubRepository
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RepositoryViewModel@ViewModelInject constructor(
    private val githubRepository: GithubRepository,
    @Assisted private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val STATE_OVERVIEW_DATA = "STATE_OVERVIEW_DATA"

    private val _repositories = MutableLiveData<List<GithubRepository>>()
    val repositories: LiveData<List<GithubRepository>> = _repositories

    fun loadRepositories() {
        //viewModelScope.launch {
        //    githubRepository.getRepositories().onEach { dataState ->
        //        when(dataState) {
        //            is DataState.Success<Boolean> -> {
        //                showProgress(false)
        //                handleData()
        //                savedStateHandle.set(STATE_OVERVIEW_DATA, true)
        //            }
        //            is DataState.Error -> {
        //                showProgress(false)
        //                if(savedStateHandle.get<Boolean>(STATE_OVERVIEW_DATA) != true) {
        //                    handleError(dataState.exception)
        //                }
        //            }
        //            is DataState.Loading -> {
        //                if(savedStateHandle.get<Boolean>(STATE_OVERVIEW_DATA) != true) {
        //                    showProgress(true)
        //                }
        //            }
        //        }
        //    }.launchIn(viewModelScope)
        //}
    }

    fun searchRepositories() {
        //viewModelScope.launch {
//
        //}
    }

    private fun handleData() {

    }
}