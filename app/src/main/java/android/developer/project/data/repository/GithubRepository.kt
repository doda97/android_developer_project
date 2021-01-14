package android.developer.project.data.repository

import android.developer.project.data.DataState
import android.developer.project.data.local.PreferenceHelper
import android.developer.project.data.remote.Api
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GithubRepository
constructor(
    private val api: Api,
    private val prefs: PreferenceHelper
) {

    fun getRepositories(): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            emit(DataState.Success(true))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun searchRepositories(searchText: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            emit(DataState.Success(true))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun getApis(): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            emit(DataState.Success(true))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}