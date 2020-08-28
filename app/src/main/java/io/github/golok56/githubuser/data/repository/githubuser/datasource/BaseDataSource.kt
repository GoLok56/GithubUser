package io.github.golok56.githubuser.data.repository.githubuser.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.github.golok56.githubuser.presentation.models.GithubUser

abstract class BaseDataSource : PageKeyedDataSource<Long, GithubUser>() {
    protected val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean>
        get() = _loading

    protected val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception>
        get() = _error
}