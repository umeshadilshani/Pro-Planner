//IT21318320 - Silva T.U.D
package com.example.proplanner

import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.proplanner.activities.BudgetDetailsActivity
import com.example.proplanner.models.Budget
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.junit.Before
import org.junit.Test

class BudgetDetailsActivityTest {

    @Before
    fun launchActivity() {
        ActivityScenario.launch(BudgetDetailsActivity::class.java)
    }

    @Test
    fun testUiElementsDisplayed() {
        // Check if all the UI elements are displayed
        onView(withId(R.id.etBudget)).check(matches(isDisplayed()))
        onView(withId(R.id.btnDelete)).check(matches(isDisplayed()))
        onView(withId(R.id.btnUpdate)).check(matches(isDisplayed()))
        onView(withId(R.id.ibBack)).check(matches(isDisplayed()))
    }

    @Test
    fun testDeleteRecord() {
        // Check if specific record is been deleted
        onView(withId(R.id.btnDelete)).perform(ViewActions.click())
        deleteRecord("Test Project")
    }

    @Test
    fun testUpdateRecord() {
        // Check if specific record is been updated
        onView(withId(R.id.btnUpdate)).perform(ViewActions.click())
        updateRecord()
    }

    private fun deleteRecord(s: String) {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("Budgets")

        // Find the child node with the projectName value
        ref.orderByChild("projectName").equalTo(s).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // If the child node exists, delete it
                    for (projectSnapshot in dataSnapshot.children) {
                        projectSnapshot.ref.removeValue()
                    }

                } else {
                    // If the child node doesn't exist, log an error message or show an alert to the user
                    Log.e("DeleteProject", "Project not found")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
                Log.e("DeleteProject", databaseError.message)
            }
        })
    }

    private fun updateRecord() {
        val projectName = "Test Project"
        val projectBudget = 800.0

        if (projectName.isNotEmpty() && projectBudget > 0) {
            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference("Budgets")

            ref.orderByChild("projectName").equalTo(projectName)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // If the child node exists, update it
                            for (projectSnapshot in dataSnapshot.children) {
                                val budget = projectSnapshot.getValue(Budget::class.java)
                                if (budget != null) {
                                    budget.projectName = projectName
                                    budget.totalBudget = projectBudget

                                    // Update the record in the database
                                    projectSnapshot.ref.setValue(budget)
                                }
                            }
                        } else {
                            // If the child node doesn't exist, log an error message or show an alert to the user
                            Log.e("UpdateProject", "Project not found")
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle errors here
                        Log.e("UpdateProject", databaseError.message)
                    }
                })
        }
    }
}