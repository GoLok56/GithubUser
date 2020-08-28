package io.github.golok56.githubuser.domain.entities

data class GithubUserEntity(
    val id: Long,
    val login: String,
    val avatar: String,
    val githubUrl: String
)