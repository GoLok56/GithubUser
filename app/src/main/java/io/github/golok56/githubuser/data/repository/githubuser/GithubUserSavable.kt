package io.github.golok56.githubuser.data.repository.githubuser

import io.github.golok56.githubuser.data.dto.GithubUserDto

interface GithubUserSavable {
    fun saveUserResult(lastSeenId: Long, githubUsersDto: List<GithubUserDto>)
    fun saveSearchResult(query: String, page: Long, githubUsersDto: List<GithubUserDto>)
}