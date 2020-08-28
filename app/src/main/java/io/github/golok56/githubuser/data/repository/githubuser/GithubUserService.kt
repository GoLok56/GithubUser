package io.github.golok56.githubuser.data.repository.githubuser

import io.github.golok56.githubuser.data.dto.GithubUserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val PAGE_SIZE = 20

interface GithubUserService {
    @GET("users?per_page=$PAGE_SIZE")
    suspend fun getUsers(@Query("since") lastSeenId: Long): Response<List<GithubUserDto>>

    @GET("search/users?per_page=$PAGE_SIZE")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Long
    ): Response<GithubUserDto.SearchResult>
}