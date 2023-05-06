package com.example.proplanner



import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.proplanner.activities.AddProduct
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddProductTest {

    @get:Rule
    var activityRule: ActivityTestRule<AddProduct> = ActivityTestRule(AddProduct::class.java)

    @Test
    fun validateInputs_emptyInputs_returnsFalse() {
        val activity = activityRule.activity

        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            // Set empty inputs
            activity.ptProName.setText("")
            activity.ptQuan.setText("")
            activity.ptSupName.setText("")
            activity.ptPri.setText("")


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
            activity.ptProName.setText("Test Project")
            activity.ptQuan.setText("Test 100")
            activity.ptSupName.setText("Test Yashod")
            activity.ptPri.setText("Test 40")


            // Validate inputs
            val isValid = activity.validateInputs()

            // Assert that all inputs are valid, so validation should pass
            assertTrue(isValid)
        }
    }

}