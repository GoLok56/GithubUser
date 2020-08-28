package io.github.golok56.githubuser.domain.repository

import io.github.golok56.githubuser.domain.entities.GithubUserEntity
import kotlinx.coroutines.flow.Flow

interface GithubUserRepository {
    fun getUsers(lastSeenId: Long): Flow<List<GithubUserEntity>>
    fun searchUsers(query: String, page: Long): Flow<List<GithubUserEntity>>
}