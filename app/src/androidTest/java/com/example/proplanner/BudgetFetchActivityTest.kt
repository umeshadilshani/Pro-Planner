//IT21318320 - Silva T.U.D
package com.example.proplanner

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.proplanner.activities.BudgetFetchActivity
import org.junit.Before
import org.junit.Test

class BudgetFetchActivityTest {
    @Before
    fun launchActivity() {
        ActivityScenario.launch(BudgetFetchActivity::class.java)
    }

    @Test
    fun searchForExistingProject() {
        // Check if specific record exists
        onView(withId(R.id.etSearch))
            .perform(typeText("Test Project"), closeSoftKeyboard())
        onView(withId(R.id.ivSearchIcon))
            .perform(click())
        onView(withText("Test Project"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun searchForNonExistingProject() {
        // Check if specific record does not exists
        onView(withId(R.id.etSearch))
            .perform(typeText("Project Z"), closeSoftKeyboard())
        onView(withId(R.id.ivSearchIcon))
            .perform(click())
        onView(withId(R.id.budgetViewLayout))
            .check(matches(isDisplayed()))
    }

    @Test
    fun clickOnBackButton() {
        // Check redirecting to budget home page
        onView(withId(R.id.ibBack))
            .perform(click())
        onView(withId(R.id.budget_home_layout))
            .check(matches(isDisplayed()))
    }
}