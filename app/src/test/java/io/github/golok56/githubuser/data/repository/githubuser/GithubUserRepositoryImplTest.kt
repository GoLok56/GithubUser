package io.github.golok56.githubuser.data.repository.githubuser

import io.github.golok56.githubuser.data.dto.GithubUserDto
import io.github.golok56.githubuser.data.mapper.GithubUserDtoToEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

class GithubUserRepositoryImplTest {
    private lateinit var localDataStore: GithubUserDataStore
    private lateinit var remoteDataStore: GithubUserDataStore
    private lateinit var githubUserRepository: GithubUserRepositoryImpl
    private val githubUserDtoToEntity = GithubUserDtoToEntity()

    private fun setUp() {
        localDataStore = mock(GithubUserLocalDataStore::class.java)
        remoteDataStore = mock(GithubUserRemoteDataStore::class.java)
        githubUserRepository =
            GithubUserRepositoryImpl(localDataStore, remoteDataStore, githubUserDtoToEntity)
    }

    @Test(expected = IllegalArgumentException::class)
    fun constructRepositoryWithUnsavableLocalDataStoreShouldThrowError() {
        localDataStore = mock(GithubUserDataStore::class.java)
        remoteDataStore = mock(GithubUserDataStore::class.java)
        githubUserRepository =
            GithubUserRepositoryImpl(localDataStore, remoteDataStore, githubUserDtoToEntity)
    }

    @Test
    fun constructRepositoryWithSavableLocalDataStoreShouldNotThrowError() {
        setUp()
    }

    @Test
    fun getUsersShouldReturnFromLocalDataStoreWhenLocalIsNotEmpty() {
        setUp()

        runBlocking {
            `when`(localDataStore.getUsers(anyLong())).thenReturn(getDummyData())
            val dummyData = getDummyData()
            val users = githubUserRepository.getUsers(anyLong())
            users.collect {
                assertEquals(it.size, dummyData.size)
                assertEquals(it[0].login, dummyData[0].login)
                verify(remoteDataStore, never()).getUsers(anyLong())
            }
        }
    }

    @Test
    fun getUsersShouldReturnFromRemoteDataStoreWhenLocalIsEmpty() {
        setUp()

        runBlocking {
            `when`(localDataStore.getUsers(anyLong())).thenReturn(emptyList())
            `when`(remoteDataStore.getUsers(anyLong())).thenReturn(getDummyData())
            val dummyData = getDummyData()
            val users = githubUserRepository.getUsers(anyLong())

            users.collect {
                assertEquals(it.size, dummyData.size)
                assertEquals(it[0].login, dummyData[0].login)
                verify(remoteDataStore, atLeastOnce()).getUsers(anyLong())
            }
        }
    }

    @Test
    fun searchUsersShouldReturnFromLocalDataStoreWhenLocalIsNotEmpty() {
        setUp()

        runBlocking {
            `when`(localDataStore.searchUsers(anyString(), anyLong())).thenReturn(getDummyData())
            val dummyData = getDummyData()
            val users = githubUserRepository.searchUsers(anyString(), anyLong())
            users.collect {
                assertEquals(it.size, dummyData.size)
                assertEquals(it[0].login, dummyData[0].login)
                verify(remoteDataStore, never()).searchUsers(anyString(), anyLong())
            }
        }
    }

    @Test
    fun searchUsersShouldReturnFromRemoteDataStoreWhenLocalIsEmpty() {
        setUp()

        runBlocking {
            `when`(localDataStore.searchUsers(anyString(), anyLong())).thenReturn(emptyList())
            `when`(remoteDataStore.searchUsers(anyString(), anyLong())).thenReturn(getDummyData())
            val dummyData = getDummyData()
            val users = githubUserRepository.searchUsers(anyString(), anyLong())
            users.collect {
                assertEquals(it.size, dummyData.size)
                assertEquals(it[0].login, dummyData[0].login)
                verify(remoteDataStore, atLeastOnce()).searchUsers(anyString(), anyLong())
            }
        }
    }

    private fun getDummyData() = listOf(
        GithubUserDto(0L, "golok56", "", ""),
        GithubUserDto(0L, "androbanana", "", ""),
        GithubUserDto(0L, "lukmannudin", "", ""),
        GithubUserDto(0L, "suarahati69", "", ""),
        GithubUserDto(0L, "ansdwuk", "", ""),
        GithubUserDto(0L, "suyatnalight", "", "")
    )
}