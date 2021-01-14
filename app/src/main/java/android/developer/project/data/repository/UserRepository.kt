package android.developer.project.data.repository

import android.developer.project.data.DataState
import android.developer.project.data.local.PreferenceHelper
import android.developer.project.data.model.rest.OwnerResponse
import android.developer.project.data.model.rest.toOwner
import android.developer.project.data.model.rest.toRepository
import android.developer.project.data.model.ui.Owner
import android.developer.project.data.model.ui.User
import android.developer.project.data.remote.Api
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class UserRepository
constructor(
    private val api: Api,
    private val prefs: PreferenceHelper
) {
    fun isUserLoggedIn(): Boolean{
        return prefs.getCurrentUser() != null
    }

    fun userLogin(username: String, password: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            if(username == "admin" && password == "admin"){
                prefs.setCurrentUser(User(username, password))
                emit(DataState.Success(true))
            } else {
                emit(DataState.Error(java.lang.Exception("Wrong username/password")))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun getGitUser(name: String): Flow<DataState<Owner>> = flow {
        emit(DataState.Loading)
        try {
            val userResponse = api.getUser(name)

            if(userResponse.isSuccessful && userResponse.body() != null) {
                emit(DataState.Success(userResponse.body()!!.toOwner() ))
            } else {
                emit(DataState.Error(HttpException(userResponse)))
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
}