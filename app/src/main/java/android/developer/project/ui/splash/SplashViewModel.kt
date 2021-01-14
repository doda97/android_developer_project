package android.developer.project.ui.splash

import android.developer.project.base.BaseViewModel
import android.developer.project.data.repository.UserRepository
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle

class SplashViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository,
    @Assisted private val savedStateHandle: SavedStateHandle) : BaseViewModel() {

    fun isUserLoggedIn() = userRepository.isUserLoggedIn()
}