package io.github.golok56.githubuser.presentation

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingVersion {
    private val countingIdlingResource = CountingIdlingResource("GLOBAL")
    val idlingResource: IdlingResource
        get() = countingIdlingResource

    fun increment() = countingIdlingResource.increment()
    fun decrement() = countingIdlingResource.decrement()
}