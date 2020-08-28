package io.github.golok56.githubuser.data.di

import dagger.Module
import dagger.Provides
import io.github.golok56.githubuser.data.GithubService
import io.github.golok56.githubuser.data.dto.GithubUserDto
import io.github.golok56.githubuser.data.mapper.GithubUserDtoToEntity
import io.github.golok56.githubuser.data.repository.githubuser.*
import io.github.golok56.githubuser.domain.entities.GithubUserEntity
import io.github.golok56.githubuser.domain.mapper.Mapper
import io.github.golok56.githubuser.domain.repository.GithubUserRepository
import javax.inject.Named

@Module
class GithubUserDataModule {
    @Provides
    fun providesGithubUserDtoToEntity(): Mapper<GithubUserDto, GithubUserEntity> =
        GithubUserDtoToEntity()

    @Provides
    @Named("githubUserLocal")
    fun providesGithubUserLocalDataStore(): GithubUserDataStore = GithubUserLocalDataStore()

    @Provides
    @Named("githubUserRemote")
    fun providesGithubUserRemoteDataStore(
        githubUserService: GithubUserService
    ): GithubUserDataStore = GithubUserRemoteDataStore(githubUserService)

    @Provides
    fun providesGithubUserService(): GithubUserService = GithubService.githubUserService

    @Provides
    fun providesGithubUserRepository(
        @Named("githubUserLocal") localDataStore: GithubUserDataStore,
        @Named("githubUserRemote") remoteDataStore: GithubUserDataStore,
        githubUserDtoToEntity: Mapper<GithubUserDto, GithubUserEntity>
    ): GithubUserRepository = GithubUserRepositoryImpl(
        localDataStore,
        remoteDataStore,
        githubUserDtoToEntity
    )
}