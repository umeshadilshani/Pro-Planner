//IT21318320 - Silva T.U.D
package com.example.proplanner

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.proplanner.activities.BudgetTipsActivity
import org.junit.After
import org.junit.Before
import org.junit.Test

class BudgetTipsActivityTest {
    private lateinit var scenario: ActivityScenario<BudgetTipsActivity>

    @Before
    fun setUp() {
        // Start the activity scenario
        scenario = ActivityScenario.launch(BudgetTipsActivity::class.java)
    }

    @After
    fun tearDown() {
        // Close the activity scenario
        scenario.close()
    }

    @Test
    fun testActivityInView() {
        // Check if the ScrollView containing the tips is displayed
        onView(withId(R.id.svTips)).check(matches(isDisplayed()))
    }

    @Test
    fun testTipsDisplayed() {
        // Check if the topics of the tips are displayed
        onView(withText("Small Business Basics: Financial Management")).check(matches(isDisplayed()))
    }
}