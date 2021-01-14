package android.developer.project.ui.main.fragment.profile

import android.developer.project.base.BaseViewModel
import android.developer.project.data.repository.GithubRepository
import android.developer.project.data.repository.UserRepository
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ProfileViewModel@ViewModelInject constructor(
    private val userRepository: UserRepository,
    @Assisted private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val STATE_OVERVIEW_DATA = "STATE_OVERVIEW_DATA"

    private val _repositories = MutableLiveData<List<GithubRepository>>()
    val repositories: LiveData<List<GithubRepository>> = _repositories

    fun loadProfile() {

    }

    private fun handleData() {

    }
}