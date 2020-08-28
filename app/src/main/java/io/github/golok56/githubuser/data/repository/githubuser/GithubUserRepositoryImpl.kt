package io.github.golok56.githubuser.data.repository.githubuser

import io.github.golok56.githubuser.data.dto.GithubUserDto
import io.github.golok56.githubuser.domain.entities.GithubUserEntity
import io.github.golok56.githubuser.domain.mapper.Mapper
import io.github.golok56.githubuser.domain.repository.GithubUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GithubUserRepositoryImpl(
    private val localDataStore: GithubUserDataStore,
    private val remoteDataStore: GithubUserDataStore,
    private val githubUserDtoToEntity: Mapper<GithubUserDto, GithubUserEntity>
) : GithubUserRepository {
    init {
        if (localDataStore !is GithubUserSavable)
            throw IllegalArgumentException("Local datastore harus implementasi interface GithubUserSavable")
    }

    override fun getUsers(lastSeenId: Long): Flow<List<GithubUserEntity>> = flow {
        var githubUsers = localDataStore.getUsers(lastSeenId)
        if (githubUsers.isEmpty()) {
            githubUsers = remoteDataStore.getUsers(lastSeenId)
            (localDataStore as GithubUserSavable).saveUserResult(lastSeenId, githubUsers)
        }

        emit(githubUsers.map(githubUserDtoToEntity::map))
    }

    override fun searchUsers(query: String, page: Long): Flow<List<GithubUserEntity>> = flow {
        var githubUsers = localDataStore.searchUsers(query, page)
        if (githubUsers.isEmpty()) {
            githubUsers = remoteDataStore.searchUsers(query, page)
            (localDataStore as GithubUserSavable).saveSearchResult(query, page, githubUsers)
        }

        emit(githubUsers.map(githubUserDtoToEntity::map))
    }
}