package io.github.golok56.githubuser.domain.usecase

import io.github.golok56.githubuser.domain.entities.GithubUserEntity
import io.github.golok56.githubuser.domain.repository.GithubUserRepository
import kotlinx.coroutines.flow.Flow

class GetGithubUsers(
    private val githubUserRepository: GithubUserRepository
) : UseCase<Long, List<GithubUserEntity>>() {
    override fun createOutput(input: Long?): Flow<List<GithubUserEntity>> {
        var lastSeenId = 0L
        input?.let { if (it > 0) lastSeenId = it }
        return githubUserRepository.getUsers(lastSeenId)
    }
}