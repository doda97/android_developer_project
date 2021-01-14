package android.developer.project.data.repository

import android.developer.project.data.DataState
import android.developer.project.data.model.rest.toRepository
import android.developer.project.data.model.ui.Repository
import android.developer.project.data.remote.Api
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GithubRepository
constructor(private val api: Api) {

    fun getRepositories(): Flow<DataState<List<Repository>?>> = flow {
        emit(DataState.Loading)
        try {
            val repositoriesResponse = api.getRepositories()

            if(repositoriesResponse.isSuccessful){
                emit(DataState.Success(repositoriesResponse.body()?.map { it.toRepository() }))
            } else {
                emit(DataState.Error(HttpException(repositoriesResponse)))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun searchRepositories(searchText: String): Flow<DataState<List<Repository>?>> = flow {
        emit(DataState.Loading)
        try {
            val repositoriesResponse = api.searchRepositoriesByUsername(searchText)

            if(repositoriesResponse.isSuccessful){
                emit(DataState.Success(repositoriesResponse.body()?.map { it.toRepository() }))
            } else {
                emit(DataState.Error(HttpException(repositoriesResponse)))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun getRepository(username: String, repositoryName: String): Flow<DataState<Repository?>> = flow {
        emit(DataState.Loading)
        try {
            val repositoriesResponse = api.getRepository(username, repositoryName)

            if(repositoriesResponse.isSuccessful){
                emit(DataState.Success(repositoriesResponse.body()?.toRepository() ))
            } else {
                emit(DataState.Error(HttpException(repositoriesResponse)))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}