package io.github.golok56.githubuser.presentation.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.golok56.githubuser.domain.entities.GithubUserEntity
import io.github.golok56.githubuser.domain.mapper.Mapper
import io.github.golok56.githubuser.domain.usecase.SearchGithubUser
import io.github.golok56.githubuser.domain.usecase.UseCase
import io.github.golok56.githubuser.presentation.models.GithubUser

class SearchViewModelFactory(
    private val getGithubUsers: UseCase<Long, List<GithubUserEntity>>,
    private val searchGithubUser: UseCase<SearchGithubUser.Input, List<GithubUserEntity>>,
    private val githubUserEntityToModel: Mapper<GithubUserEntity, GithubUser>
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java))
            return SearchViewModel(
                getGithubUsers,
                searchGithubUser,
                githubUserEntityToModel
            ) as T
        throw IllegalArgumentException()
    }
}