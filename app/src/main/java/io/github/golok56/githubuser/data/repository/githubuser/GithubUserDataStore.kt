package io.github.golok56.githubuser.data.repository.githubuser

import io.github.golok56.githubuser.data.dto.GithubUserDto

interface GithubUserDataStore {
    suspend fun getUsers(lastSeenId: Long): List<GithubUserDto>
    suspend fun searchUsers(query: String, page: Long): List<GithubUserDto>
}