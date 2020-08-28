package io.github.golok56.githubuser.presentation.feature.search.di

import dagger.Component
import io.github.golok56.githubuser.data.di.GithubUserDataModule
import io.github.golok56.githubuser.domain.di.GithubUserDomainModule
import io.github.golok56.githubuser.presentation.feature.search.SearchFragment
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class SearchScope

@SearchScope
@Component(
    modules = [GithubUserDataModule::class, GithubUserDomainModule::class, SearchModule::class]
)
interface SearchComponent {
    fun inject(fragment: SearchFragment)
}