package io.github.golok56.githubuser.data.repository.githubuser.datasource

import androidx.paging.DataSource
import io.github.golok56.githubuser.domain.entities.GithubUserEntity
import io.github.golok56.githubuser.domain.mapper.Mapper
import io.github.golok56.githubuser.domain.usecase.SearchGithubUser
import io.github.golok56.githubuser.domain.usecase.UseCase
import io.github.golok56.githubuser.presentation.models.GithubUser
import kotlinx.coroutines.CoroutineScope

class SearchResultDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val searchGithubUser: UseCase<SearchGithubUser.Input, List<GithubUserEntity>>,
    private val githubUserEntityToModel: Mapper<GithubUserEntity, GithubUser>,
    private val query: String
) : BaseDataSourceFactory() {
    override fun create(): DataSource<Long, GithubUser> {
        val searchResultDataSource = SearchResultDataSource(
            coroutineScope,
            searchGithubUser,
            githubUserEntityToModel,
            query
        )
        _dataSource.postValue(searchResultDataSource)
        return searchResultDataSource
    }
}