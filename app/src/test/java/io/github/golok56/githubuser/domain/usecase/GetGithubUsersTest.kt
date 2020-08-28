package io.github.golok56.githubuser.domain.usecase

import io.github.golok56.githubuser.domain.repository.GithubUserRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class GetGithubUsersTest {
    lateinit var githubUserRepository: GithubUserRepository
    private lateinit var getGithubUsers: GetGithubUsers

    @Before
    fun setUp() {
        githubUserRepository = mock(GithubUserRepository::class.java)
        getGithubUsers = GetGithubUsers(githubUserRepository)
    }

    @Test
    fun getUsersWithoutParameterShouldReturnFromRepositoryWithPageOne() {
        getGithubUsers()
        verify(githubUserRepository, atLeastOnce()).getUsers(0)
    }

    @Test
    fun getUsersShoulReturnFromRepositoryEqualWithParameter() {
        val dummyPages = arrayOf(1L, 5, 10, 20, 100, 1000)
        for (dummyPage in dummyPages) {
            getGithubUsers(dummyPage)
            verify(githubUserRepository, atLeastOnce()).getUsers(dummyPage)
        }
    }
}