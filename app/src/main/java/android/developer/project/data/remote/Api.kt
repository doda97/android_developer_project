package android.developer.project.data.remote

import android.developer.project.data.model.rest.OwnerResponse
import android.developer.project.data.model.rest.RepositoryResponse
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @GET("users/doda97/repos")
    suspend fun getRepositories(): Response<List<RepositoryResponse>>

    @GET("users/{searchText}/repos")
    suspend fun searchRepositoriesByUsername(@Path("searchText") searchText: String): Response<List<RepositoryResponse>>

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<OwnerResponse>

    @GET("repos/{username}/{repoName}")
    suspend fun getRepository(@Path("username") username: String, @Path("repoName") repoName: String): Response<RepositoryResponse>
}