package android.developer.project.data.local

import android.developer.project.data.model.ui.User

interface PreferenceHelper {

    fun getCurrentUser(): User?

    fun setCurrentUser(user: User?)
}