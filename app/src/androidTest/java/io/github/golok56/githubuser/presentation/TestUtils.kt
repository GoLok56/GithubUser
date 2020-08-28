package io.github.golok56.githubuser.presentation

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher
import org.hamcrest.Matchers

/**
 * Created by dannyroa on 5/9/15.
 */
object TestUtils {
    fun <VH : RecyclerView.ViewHolder?> actionOnItemViewAtPosition(
        position: Int,
        @IdRes viewId: Int,
        viewAction: ViewAction
    ): ViewAction {
        return ActionOnItemViewAtPositionViewAction<RecyclerView.ViewHolder?>(
            position,
            viewId,
            viewAction
        )
    }

    fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(
            recyclerViewId
        )
    }

    private class ActionOnItemViewAtPositionViewAction<VH : RecyclerView.ViewHolder?> constructor(
        private val position: Int,
        @IdRes private val viewId: Int,
        private val viewAction: ViewAction
    ) : ViewAction {

        override fun getConstraints(): Matcher<View> = Matchers.allOf<View>(
            ViewMatchers.isAssignableFrom(RecyclerView::class.java), ViewMatchers.isDisplayed()
        )

        override fun getDescription() =
            "actionOnItemAtPosition performing ViewAction: ${viewAction.description} on item at position: $position"

        override fun perform(uiController: UiController, view: View) {
            val recyclerView: RecyclerView = view as RecyclerView
            ScrollToPositionViewAction(position).perform(uiController, view)
            uiController.loopMainThreadUntilIdle()
            val targetView: View = recyclerView.getChildAt(position).findViewById(viewId)
            viewAction.perform(uiController, targetView)
        }

    }

    private class ScrollToPositionViewAction constructor(
        private val position: Int
    ) : ViewAction {
        override fun getDescription(): String = "scroll RecyclerView to position: $position"
        override fun perform(uiController: UiController?, view: View) {
            val recyclerView: RecyclerView = view as RecyclerView
            recyclerView.scrollToPosition(position)
        }

        override fun getConstraints(): Matcher<View> = Matchers.allOf<View>(
            ViewMatchers.isAssignableFrom(RecyclerView::class.java), ViewMatchers.isDisplayed()
        )
    }
}