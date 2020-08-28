package io.github.golok56.githubuser.domain.usecase

import io.github.golok56.githubuser.domain.repository.GithubUserRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class SearchGithubUserTest {
    private lateinit var githubUserRepository: GithubUserRepository
    private lateinit var searchGithubUser: SearchGithubUser

    @Before
    fun setUp() {
        githubUserRepository = mock(GithubUserRepository::class.java)
        searchGithubUser = SearchGithubUser(githubUserRepository)
    }

    @Test(expected = NoInputException::class)
    fun searchUserWithoutParameterShouldThrowError() {
        searchGithubUser()
    }

    @Test
    fun searchUserWithPageLessThanZeroShouldCallRepositoryWithPageEqualToOne() {
        val dummyQuery = "Satria"
        val dummyPages = arrayOf(0L, -1, -5, -10, -100, -1000)
        for (dummyPage in dummyPages) {
            searchGithubUser(SearchGithubUser.Input(page = dummyPage, query = dummyQuery))
        }
        verify(githubUserRepository, times(dummyPages.size))
            .searchUsers(page = 1, query = dummyQuery)
    }

    @Test(expected = IllegalArgumentException::class)
    fun searchUserWithEmptyQueryShouldThrowError() {
        searchGithubUser(SearchGithubUser.Input(page = 1, query = ""))
    }

    @Test(expected = IllegalArgumentException::class)
    fun searchUserWithQueryFilledBySpaceShouldThrowError() {
        searchGithubUser(SearchGithubUser.Input(page = 1, query = "       "))
    }
}