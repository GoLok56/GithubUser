package io.github.golok56.githubuser.domain.usecase

import io.github.golok56.githubuser.domain.entities.GithubUserEntity
import io.github.golok56.githubuser.domain.repository.GithubUserRepository
import kotlinx.coroutines.flow.Flow

class SearchGithubUser(
    private val githubUserRepository: GithubUserRepository
) : UseCase<SearchGithubUser.Input, List<GithubUserEntity>>() {
    override fun createOutput(input: Input?): Flow<List<GithubUserEntity>> {
        if (input == null) throw NoInputException(CLASS_NAME)

        if (input.query.trim().isEmpty()) throw IllegalArgumentException(
            "Query tidak boleh kosong pada kelas SearchGithubUser.Input"
        )

        val query = input.query
        var page = 1L
        if (input.page > 1) page = input.page

        return githubUserRepository.searchUsers(query, page)
    }

    data class Input(
        val query: String,
        val page: Long
    )

    companion object {
        private const val CLASS_NAME = "SearchGithubUser"
    }
}