@file:Suppress("DEPRECATION")

package com.example.proplanner

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.proplanner.activities.CreatePlan
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreatePlanTest {

    @get:Rule
    var activityRule: ActivityTestRule<CreatePlan> = ActivityTestRule(CreatePlan::class.java)

    @Test
    fun validateInputs_emptyInputs_returnsFalse() {
        val activity = activityRule.activity

        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            // Set empty inputs
            activity.binding.projectName.setText("")
            activity.binding.projectDescription.setText("")
            activity.binding.timePeriod.setText("")
            activity.binding.task.setText("")

            // Validate inputs
            val isValid = activity.validateInputs()

            // Assert that all inputs are empty, so validation should fail
            assertFalse(isValid)
        }
    }



    @Test
    fun validateInputs_validInputs_returnsTrue() {
        val activity = activityRule.activity

        activity.runOnUiThread {
            // Set valid inputs
            activity.binding.projectName.setText("Test Project")
            activity.binding.projectDescription.setText("Test Description")
            activity.binding.timePeriod.setText("Test Time Period")
            activity.binding.task.setText("Test Task")

            // Validate inputs
            val isValid = activity.validateInputs()

            // Assert that all inputs are valid, so validation should pass
            assertTrue(isValid)
        }
    }

}


