package io.github.golok56.githubuser.data.repository.githubuser.datasource

import io.github.golok56.githubuser.domain.entities.GithubUserEntity
import io.github.golok56.githubuser.domain.mapper.Mapper
import io.github.golok56.githubuser.domain.usecase.UseCase
import io.github.golok56.githubuser.presentation.EspressoIdlingVersion
import io.github.golok56.githubuser.presentation.models.GithubUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserResultDataSource(
    private val coroutineScope: CoroutineScope,
    private val getGithubUsers: UseCase<Long, List<GithubUserEntity>>,
    private val githubUserEntityToModel: Mapper<GithubUserEntity, GithubUser>
) : BaseDataSource() {
    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, GithubUser>
    ) {
        coroutineScope.launch {
            EspressoIdlingVersion.increment()
            try {
                getGithubUsers().collect {
                    val lastSeenId = it.last().id
                    callback.onResult(it.map(githubUserEntityToModel::map), null, lastSeenId)
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
                getGithubUsers(params.key).collect {
                    val lastSeenId = it.last().id
                    callback.onResult(it.map(githubUserEntityToModel::map), lastSeenId)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                _error.postValue(ex)
            } finally {
                EspressoIdlingVersion.decrement()
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, GithubUser>
    ) {
    }
}