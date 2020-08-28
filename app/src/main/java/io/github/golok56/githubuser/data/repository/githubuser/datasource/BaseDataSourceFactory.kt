package io.github.golok56.githubuser.data.repository.githubuser.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.github.golok56.githubuser.presentation.models.GithubUser

abstract class BaseDataSourceFactory : DataSource.Factory<Long, GithubUser>() {
    protected var _dataSource = MutableLiveData<BaseDataSource>()
    val dataSource: LiveData<BaseDataSource>
        get() = _dataSource
}