package android.developer.project.base

import android.developer.project.R
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions

abstract class BaseActivity : AppCompatActivity() {

    val navigationOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.right_to_left_enter)
        .setExitAnim(R.anim.right_to_left_exit)
        .setPopEnterAnim(R.anim.left_to_right_enter)
        .setPopExitAnim(R.anim.left_to_right_exit)
}