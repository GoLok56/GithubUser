package io.github.golok56.githubuser.presentation.feature.search

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.github.golok56.githubuser.data.repository.githubuser.PAGE_SIZE
import io.github.golok56.githubuser.data.repository.githubuser.datasource.BaseDataSourceFactory
import io.github.golok56.githubuser.data.repository.githubuser.datasource.SearchResultDataSourceFactory
import io.github.golok56.githubuser.data.repository.githubuser.datasource.UserResultDataSourceFactory
import io.github.golok56.githubuser.domain.entities.GithubUserEntity
import io.github.golok56.githubuser.domain.mapper.Mapper
import io.github.golok56.githubuser.domain.usecase.SearchGithubUser
import io.github.golok56.githubuser.domain.usecase.UseCase
import io.github.golok56.githubuser.presentation.EspressoIdlingVersion
import io.github.golok56.githubuser.presentation.models.GithubUser
import java.util.concurrent.Executors

class SearchViewModel(
    private val getGithubUsers: UseCase<Long, List<GithubUserEntity>>,
    private val searchGithubUser: UseCase<SearchGithubUser.Input, List<GithubUserEntity>>,
    private val githubUserEntityToModel: Mapper<GithubUserEntity, GithubUser>
) : ViewModel() {
    private val _query = MutableLiveData<String>("")

    private val dataSourceFactory = MutableLiveData<BaseDataSourceFactory>()

    val loading = dataSourceFactory.switchMap { factory ->
        factory.dataSource.switchMap { dataSource ->
            dataSource.loading.switchMap { isLoading ->
                liveData { emit(isLoading) }
            }
        }
    }

    val error = dataSourceFactory.switchMap { factory ->
        factory.dataSource.switchMap { dataSource ->
            dataSource.error.switchMap {
                liveData { emit(it) }
            }
        }
    }

    val githubUsers: LiveData<PagedList<GithubUser>> = _query.switchMap {
        dataSourceFactory.value = if (it.isEmpty()) UserResultDataSourceFactory(
            viewModelScope,
            getGithubUsers,
            githubUserEntityToModel
        ) else SearchResultDataSourceFactory(
            viewModelScope,
            searchGithubUser,
            githubUserEntityToModel,
            it
        )
        buildLivePagedList(dataSourceFactory.value!!)
    }

    private fun buildDefaultPageListConfig() = PagedList.Config
        .Builder()
        .setPageSize(PAGE_SIZE)
        .build()

    private fun buildLivePagedList(
        factory: DataSource.Factory<Long, GithubUser>
    ) = LivePagedListBuilder(factory, buildDefaultPageListConfig())
        .setFetchExecutor(Executors.newFixedThreadPool(5))
        .build()

    fun updateQuery(newQuery: String) = _query.postValue(newQuery.trim())
}