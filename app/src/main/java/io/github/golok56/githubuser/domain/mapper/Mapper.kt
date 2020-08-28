package io.github.golok56.githubuser.domain.mapper

interface Mapper<From, To> {
    fun map(from: From): To
}