package android.developer.project.data.repository

import android.developer.project.data.DataState
import android.developer.project.data.local.PreferenceHelper
import android.developer.project.data.model.ui.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository
constructor(
    private val prefs: PreferenceHelper
) {
    fun isUserLoggedIn(): Boolean{
        return prefs.getCurrentUser() != null
    }

    fun userLogin(username: String, password: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            if(username == "ADMIN" && password == "PASSWORD"){
                prefs.setCurrentUser(User(username, password))
                emit(DataState.Success(true))
            } else {
                emit(DataState.Success(false))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun userLogout() {
        prefs.setCurrentUser(null)
    }

    fun getUser(): User? {
        return prefs.getCurrentUser()
    }

    fun saveUser(user: User) {
        prefs.setCurrentUser(user)
    }
}