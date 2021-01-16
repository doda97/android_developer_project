package android.developer.project.data.remote

import android.developer.project.data.model.rest.OwnerResponse
import android.developer.project.data.model.rest.RepositoryResponse
import android.developer.project.data.model.rest.RepositorySearchResponse
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @GET("/search/repositories")
    suspend fun getRepositories(@Query("q") searchText: String, @Query("per_page") perPage: Int): Response<RepositorySearchResponse>

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<OwnerResponse>

    @GET("repos/{username}/{repoName}")
    suspend fun getRepository(@Path("username") username: String, @Path("repoName") repoName: String): Response<RepositoryResponse>
}