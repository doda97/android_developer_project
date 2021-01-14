package android.developer.project.data.remote

import android.developer.project.data.model.rest.OwnerResponse
import android.developer.project.data.model.rest.RepositoryResponse
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @GET("users/doda97/repos")
    suspend fun getRepositories(): Response<List<RepositoryResponse>>

    @GET("users/{searchText}/repos")
    suspend fun searchRepositories(@Body searchText: String): Response<List<RepositoryResponse>>

    @GET("users/{name}")
    fun getUser(name: String): Response<OwnerResponse>
}