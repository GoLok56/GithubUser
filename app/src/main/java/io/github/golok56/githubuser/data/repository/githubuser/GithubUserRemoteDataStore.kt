package io.github.golok56.githubuser.data.repository.githubuser

import io.github.golok56.githubuser.data.dto.GithubUserDto

class GithubUserRemoteDataStore(
    private val githubUserService: GithubUserService
) : GithubUserDataStore {
    override suspend fun getUsers(lastSeenId: Long): List<GithubUserDto> {
        val response = githubUserService.getUsers(lastSeenId)
        if (response.code() == 403) throw LimitReachedException()
        return response.body().orEmpty()
    }

    override suspend fun searchUsers(query: String, page: Long): List<GithubUserDto> {
        val response = githubUserService.searchUsers(query, page)
        if (response.code() == 403) throw LimitReachedException()
        return response.body()?.items.orEmpty()
    }
}