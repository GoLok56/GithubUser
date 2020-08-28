package io.github.golok56.githubuser.data.repository.githubuser.datasource

import androidx.paging.DataSource
import io.github.golok56.githubuser.domain.entities.GithubUserEntity
import io.github.golok56.githubuser.domain.mapper.Mapper
import io.github.golok56.githubuser.domain.usecase.UseCase
import io.github.golok56.githubuser.presentation.models.GithubUser
import kotlinx.coroutines.CoroutineScope

class UserResultDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val getGithubUsers: UseCase<Long, List<GithubUserEntity>>,
    private val githubUserEntityToModel: Mapper<GithubUserEntity, GithubUser>
) : BaseDataSourceFactory() {
    override fun create(): DataSource<Long, GithubUser> {
        val userResultDataSource = UserResultDataSource(
            coroutineScope,
            getGithubUsers,
            githubUserEntityToModel
        )
        _dataSource.postValue(userResultDataSource)
        return userResultDataSource
    }
}