package com.android.interviewtask.candyspace

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class InterviewTaskUITest {
    /******
     *  ! Important - Before start testing please uncomment the testing code lines 36 and comment 41-47 in UserViewModel.kt.
     */

    @Rule
    @JvmField
    public val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(
        MainActivity::class.java,
        true,
        true
    )

    @Test
    fun testSampleRecyclerVisible() {
        Espresso.onView(ViewMatchers.withId(R.id.rcv_userslist))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(activityRule.getActivity().getWindow().getDecorView())
                )
            )
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testCaseForRecyclerItemView() {
        Espresso.onView(ViewMatchers.withId(R.id.rcv_userslist))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(activityRule.activity.window.decorView)
                )
            )
            .check(
                ViewAssertions.matches(
                    withViewAtPosition(
                        1, Matchers.allOf(
                            ViewMatchers.withId(R.id.lay_root), ViewMatchers.isDisplayed()
                        )
                    )
                )
            )
    }

    fun withViewAtPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?>? {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView)
            }
        }
    }
}