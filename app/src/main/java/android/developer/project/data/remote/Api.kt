package android.developer.project.data.remote

import android.developer.project.data.model.rest.RepositoryResponse
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @GET("https://developer.github.com/v3")
    suspend fun getRepositories(@Body searchText: String): Response<RepositoryResponse>

    @GET("https://developer.github.com/v3/search/#search-repositories")
    suspend fun searchRepositories(@Body searchText: String): Response<RepositoryResponse>

    @GET("https://developer.github.com/v3/search/#search-repositories")
    suspend fun getApi(@Body searchText: String): Response<RepositoryResponse>
}