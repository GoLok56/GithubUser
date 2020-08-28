package io.github.golok56.githubuser.data.repository.githubuser

import io.github.golok56.githubuser.data.dto.GithubUserDto

class GithubUserLocalDataStore : GithubUserDataStore, GithubUserSavable {
    private val githubUsers = mutableMapOf<Long, List<GithubUserDto>>()
    private val searchResults = mutableMapOf<String, List<GithubUserDto>>()

    override suspend fun getUsers(lastSeenId: Long): List<GithubUserDto> =
        githubUsers[lastSeenId].orEmpty()

    override suspend fun searchUsers(query: String, page: Long): List<GithubUserDto> {
        return searchResults[generateSearchResultKey(query, page)].orEmpty()
    }

    override fun saveUserResult(lastSeenId: Long, githubUsersDto: List<GithubUserDto>) {
        githubUsers[lastSeenId] = githubUsersDto
    }

    override fun saveSearchResult(query: String, page: Long, githubUsersDto: List<GithubUserDto>) {
        searchResults[generateSearchResultKey(query, page)]
    }

    private fun generateSearchResultKey(query: String, page: Long) = "$query$page"
}