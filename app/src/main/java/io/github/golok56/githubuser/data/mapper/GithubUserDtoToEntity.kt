package io.github.golok56.githubuser.data.mapper

import io.github.golok56.githubuser.data.dto.GithubUserDto
import io.github.golok56.githubuser.domain.entities.GithubUserEntity
import io.github.golok56.githubuser.domain.mapper.Mapper

class GithubUserDtoToEntity : Mapper<GithubUserDto, GithubUserEntity> {
    override fun map(from: GithubUserDto) = GithubUserEntity(
        from.id,
        from.login,
        from.avatar,
        from.githubUrl
    )
}