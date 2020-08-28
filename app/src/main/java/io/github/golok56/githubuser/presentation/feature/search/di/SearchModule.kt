package io.github.golok56.githubuser.presentation.feature.search.di

import dagger.Module
import dagger.Provides
import io.github.golok56.githubuser.domain.entities.GithubUserEntity
import io.github.golok56.githubuser.domain.mapper.Mapper
import io.github.golok56.githubuser.domain.usecase.SearchGithubUser
import io.github.golok56.githubuser.domain.usecase.UseCase
import io.github.golok56.githubuser.presentation.feature.search.SearchViewModelFactory
import io.github.golok56.githubuser.presentation.mapper.GithubUserEntityToModel
import io.github.golok56.githubuser.presentation.models.GithubUser

@Module
class SearchModule {
    @Provides
    fun providesSearchViewModelFactory(
        getGithubUsers: UseCase<Long, List<GithubUserEntity>>,
        searchGithubUser: UseCase<SearchGithubUser.Input, List<GithubUserEntity>>,
        githubUserEntityToModel: Mapper<GithubUserEntity, GithubUser>
    ) = SearchViewModelFactory(
        getGithubUsers,
        searchGithubUser,
        githubUserEntityToModel
    )

    @Provides
    fun providesGithubUserEntityToModel(): Mapper<GithubUserEntity, GithubUser> =
        GithubUserEntityToModel()
}