package io.github.golok56.githubuser.data.repository.githubuser.datasource

import io.github.golok56.githubuser.domain.entities.GithubUserEntity
import io.github.golok56.githubuser.domain.mapper.Mapper
import io.github.golok56.githubuser.domain.usecase.SearchGithubUser
import io.github.golok56.githubuser.domain.usecase.UseCase
import io.github.golok56.githubuser.presentation.EspressoIdlingVersion
import io.github.golok56.githubuser.presentation.models.GithubUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchResultDataSource(
    private val coroutineScope: CoroutineScope,
    private val searchGithubUser: UseCase<SearchGithubUser.Input, List<GithubUserEntity>>,
    private val githubUserEntityToModel: Mapper<GithubUserEntity, GithubUser>,
    private val query: String
) : BaseDataSource() {
    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, GithubUser>
    ) {
        coroutineScope.launch {
            EspressoIdlingVersion.increment()
            try {
                searchGithubUser(SearchGithubUser.Input(query, 1)).collect {
                    callback.onResult(it.map(githubUserEntityToModel::map), null, 2)
                    _loading.postValue(false)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                _error.postValue(ex)
            } finally {
                EspressoIdlingVersion.decrement()
            }
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, GithubUser>) {
        coroutineScope.launch {
            EspressoIdlingVersion.increment()
            try {
                searchGithubUser(SearchGithubUser.Input(query, params.key)).collect {
                    callback.onResult(it.map(githubUserEntityToModel::map), params.key + 1)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                _error.postValue(ex)
            } finally {
                EspressoIdlingVersion.decrement()
            }
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, GithubUser>) {
    }
}