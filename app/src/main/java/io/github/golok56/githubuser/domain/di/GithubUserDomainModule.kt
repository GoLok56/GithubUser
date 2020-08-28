package io.github.golok56.githubuser.domain.di

import dagger.Module
import dagger.Provides
import io.github.golok56.githubuser.domain.entities.GithubUserEntity
import io.github.golok56.githubuser.domain.repository.GithubUserRepository
import io.github.golok56.githubuser.domain.usecase.GetGithubUsers
import io.github.golok56.githubuser.domain.usecase.SearchGithubUser
import io.github.golok56.githubuser.domain.usecase.UseCase

@Module
class GithubUserDomainModule {
    @Provides
    fun providesGetGithubUsers(
        githubUserRepository: GithubUserRepository
    ): UseCase<Long, List<GithubUserEntity>> = GetGithubUsers(githubUserRepository)

    @Provides
    fun providesSearchGithubUsers(
        githubUserRepository: GithubUserRepository
    ): UseCase<SearchGithubUser.Input, List<GithubUserEntity>> =
        SearchGithubUser(githubUserRepository)
}