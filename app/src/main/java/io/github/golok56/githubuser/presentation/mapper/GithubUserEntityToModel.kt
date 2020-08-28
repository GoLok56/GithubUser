package io.github.golok56.githubuser.presentation.mapper

import io.github.golok56.githubuser.domain.entities.GithubUserEntity
import io.github.golok56.githubuser.domain.mapper.Mapper
import io.github.golok56.githubuser.presentation.models.GithubUser

class GithubUserEntityToModel : Mapper<GithubUserEntity, GithubUser> {
    override fun map(from: GithubUserEntity) = GithubUser(
        from.login,
        from.avatar,
        from.githubUrl
    )
}