//IT21318320 - Silva T.U.D
package com.example.proplanner

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.proplanner.activities.BudgetInsightActivity
import org.junit.After
import org.junit.Before
import org.junit.Test

class BudgetInsightActivityTest {
    private lateinit var scenario: ActivityScenario<BudgetInsightActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(BudgetInsightActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun backButtonShouldNavigateToBudgetHomeActivity() {
        // Check redirecting to budget home page
        onView(withId(R.id.ibBack)).perform(click())
        onView(withId(R.id.budget_home_layout)).check(matches(isDisplayed()))
    }
}
