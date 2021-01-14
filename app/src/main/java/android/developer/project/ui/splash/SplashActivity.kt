package android.developer.project.ui.splash

import android.content.Intent
import android.developer.project.ui.main.MainActivity
import android.developer.project.base.BaseActivity
import android.developer.project.ui.login.LoginActivity
import androidx.activity.viewModels
import androidx.navigation.ActivityNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity: BaseActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onStart() {
        super.onStart()

        val navigator = ActivityNavigator(this)

        val destination = if (splashViewModel.isUserLoggedIn()) {
            navigator
                .createDestination()
                .setIntent(Intent(this, MainActivity::class.java))
        } else {
            navigator
                .createDestination()
                .setIntent(Intent(this, LoginActivity::class.java))
        }

        navigator.navigate(destination, null, null, null)
        finish()
    }
}