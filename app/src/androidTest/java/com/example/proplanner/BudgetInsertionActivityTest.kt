//IT21318320 - Silva T.U.D
package com.example.proplanner

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.proplanner.activities.BudgetFetchActivity
import com.example.proplanner.activities.BudgetInsertionActivity
import com.example.proplanner.models.Budget
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BudgetInsertionActivityTest {
    private lateinit var activity: BudgetInsertionActivity

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(BudgetInsertionActivity::class.java)

    @Before
    fun setUp() {
        activityScenarioRule.scenario.onActivity { currentActivity ->
            activity = currentActivity
        }
    }

    @Test
    fun testSaveBudgetClicked_emptyFields() {
        // Test if toast message shown for empty fields
        val projectName = ""

        activity.runOnUiThread {
            activity.etProjectName.setText(projectName)

            activity.saveBudgetClicked(View(activity))
        }

        val budgetRef = FirebaseDatabase.getInstance().getReference("Budgets")
        budgetRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val budget = snapshot.children.last().getValue(Budget::class.java)
                assertNotEquals(projectName, budget?.projectName)
            }

            override fun onCancelled(error: DatabaseError) {
                fail("Database error: ${error.message}")
            }
        })
    }

    @Test
    fun testSaveBudgetClicked() {
        // Check if budget record inserted successfully
        val projectName = "Test Project"
        val projectBudget = "1000.0"

        activity.runOnUiThread {
            activity.etProjectName.setText(projectName)
            activity.etProjectBudget.setText(projectBudget)

            val categoryView = LayoutInflater.from(activity).inflate(R.layout.category_item, null, false)
            val etCatName = categoryView.findViewById<EditText>(R.id.etCatName)
            val etCatBudget = categoryView.findViewById<EditText>(R.id.etCatBudget)

            etCatName.setText("Test Category")
            etCatBudget.setText("500.0")
            activity.categoriesLayout.addView(categoryView)

            activity.saveBudgetClicked(View(activity))
        }

        val budgetRef = FirebaseDatabase.getInstance().getReference("Budgets")
        budgetRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val budget = snapshot.children.last().getValue(Budget::class.java)
                assertEquals(projectName, budget?.projectName)
                assertEquals(projectBudget.toDouble(), budget?.totalBudget)
                assertEquals(500.0, budget?.available)
                assertEquals(1, budget?.categories?.size)
            }

            override fun onCancelled(error: DatabaseError) {
                fail("Database error: ${error.message}")
            }
        })
    }
}
