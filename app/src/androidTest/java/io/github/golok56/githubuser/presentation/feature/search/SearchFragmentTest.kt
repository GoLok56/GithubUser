package io.github.golok56.githubuser.presentation.feature.search

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.golok56.githubuser.R
import io.github.golok56.githubuser.presentation.EspressoIdlingVersion
import io.github.golok56.githubuser.presentation.MainActivity
import io.github.golok56.githubuser.presentation.TestUtils.withRecyclerView
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingVersion.idlingResource)
    }

    @Test
    fun shouldSuccessWithoutError() {
        onView(withRecyclerView(R.id.rvGithubUsers).atPositionOnView(0, R.id.tvGithubUserLogin))
            .check(matches(withText("mojombo")))

        onView(withId(R.id.etSearchUser)).perform(typeText("golok"))
        Thread.sleep(1000L)
        onView(withRecyclerView(R.id.rvGithubUsers).atPositionOnView(1, R.id.tvGithubUserLogin))
            .check(matches(withText("GoLok56")))

        onView(withId(R.id.etSearchUser))
            .perform(clearText())
            .perform(typeText("asdasdasdasdasdasdasd"))
        Thread.sleep(1000L)
        onView(withId(R.id.vError)).check(matches(isDisplayed()))
        onView(withId(R.id.btnError)).check(matches(not(isDisplayed())))
    }
}