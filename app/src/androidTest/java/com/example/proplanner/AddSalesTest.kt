package com.example.proplanner



import com.example.proplanner.activities.AddSales




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
class AddSalesTest {

    @get:Rule
    var activityRule: ActivityTestRule<AddSales> = ActivityTestRule(AddSales::class.java)

    @Test
    fun validateInputs_emptyInputs_returnsFalse() {
        val activity = activityRule.activity

        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            // Set empty inputs
            activity.binding.AprName.setText("")
            activity.binding.AprBatchNo.setText("")
            activity.binding.AprQuan.setText("")
            activity.binding.AprUniPri.setText("")
            activity.binding.AprDate.setText("")

            // Validate inputs
            val isValid = activity.validateFields()

            // Assert that all inputs are empty, so validation should fail
            assertFalse(isValid)
        }
    }



    @Test
    fun validateInputs_validInputs_returnsTrue() {
        val activity = activityRule.activity

        activity.runOnUiThread {
            // Set valid inputs

            activity.binding.AprName.setText("Test Project")
            activity.binding.AprBatchNo.setText("Test 100")
            activity.binding.AprQuan.setText("Test 1000")
            activity.binding.AprUniPri.setText("Test 200")
            activity.binding.AprDate.setText("Test 01/01/2023")

            // Validate inputs
            val isValid = activity.validateFields()

            // Assert that all inputs are valid, so validation should pass
            assertTrue(isValid)
        }
    }

}