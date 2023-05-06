//IT21318320 - Silva T.U.D
package com.example.proplanner

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.proplanner.activities.BudgetHomeActivity
import org.junit.Test

class BudgetHomeActivityTest {
    @Test
    fun testNavigateCreateBudget() {
        // Check redirecting to create budget page
        val activityScenario = ActivityScenario.launch(BudgetHomeActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.tvCreateBudget)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.budgetInsertionLayout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        activityScenario.close()
    }

    @Test
    fun testNavigateViewBudget() {
        // Check redirecting to view budget page
        val activityScenario = ActivityScenario.launch(BudgetHomeActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.tvViewBudget)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.budgetViewLayout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        activityScenario.close()
    }

    @Test
    fun testNavigateBudgetInsight() {
        // Check redirecting to budget insight page
        val activityScenario = ActivityScenario.launch(BudgetHomeActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.tvInsight)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.budgetInsightLayout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        activityScenario.close()
    }

    @Test
    fun testNavigateTips() {
        // Check redirecting to budget tips page
        val activityScenario = ActivityScenario.launch(BudgetHomeActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.tvTipsI)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.budgetTipsLayout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        activityScenario.close()
    }
}