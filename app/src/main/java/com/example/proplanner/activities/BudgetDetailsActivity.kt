//IT21318320 - Silva T.U.D
package com.example.proplanner.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.proplanner.R
import com.example.proplanner.models.Budget
import com.example.proplanner.models.Category
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BudgetDetailsActivity : AppCompatActivity() {
    //Declaring variables
    private lateinit var tvProjectName: TextView
    private lateinit var etBudget: EditText
    private lateinit var btnDelete: Button
    private lateinit var btnUpdate: Button
    private lateinit var ibBack : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_details)

        //Initializing variables
        tvProjectName = findViewById(R.id.tvFullAmount)
        etBudget = findViewById(R.id.etBudget)
        btnDelete = findViewById(R.id.btnDelete)
        btnUpdate = findViewById(R.id.btnUpdate)
        ibBack = findViewById(R.id.ibBack)

        ibBack.setOnClickListener{
            //Updated back button
            var intent = Intent(this, BudgetFetchActivity::class.java)
            startActivity(intent)
        }

        //Setting budget details
        tvProjectName.text = intent.getStringExtra("projectName")
        etBudget.setText(intent.getDoubleExtra("projectBudget", 0.0).toString())

        val categories = intent.getParcelableArrayListExtra<Category>("categories")

        val linearLayout = findViewById<LinearLayout>(R.id.linear_layout_cat)

        if (categories != null) {
            for (category in categories) {
                val horizontalLayout = LinearLayout(this)
                horizontalLayout.orientation = LinearLayout.HORIZONTAL

                val editTextViewName = EditText(this)
                editTextViewName.inputType =
                    InputType.TYPE_CLASS_TEXT
                editTextViewName.textSize = 20f
                val layoutParams1 = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams1.weight = 1.0f
                layoutParams1.width = 0
                editTextViewName.setLayoutParams(layoutParams1)
                editTextViewName.setText(category.name.toString())

                val editTextView = EditText(this)
                editTextView.inputType =
                    InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                editTextView.textSize = 20f
                val layoutParams2 = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams2.weight = 1.0f
                editTextView.setLayoutParams(layoutParams2)
                editTextView.setText(category.amount.toString())

                horizontalLayout.addView(editTextViewName)
                horizontalLayout.addView(editTextView)
                linearLayout.addView(horizontalLayout)
            }
        }

        btnDelete.setOnClickListener {
            deleteRecord(tvProjectName.text.toString())
        }

        btnUpdate.setOnClickListener {
            updateBudgetRecord()
        }
    }

    //Updating budget records
    private fun updateBudgetRecord() {
        //Accessing new data
        val projectName = tvProjectName.text.toString().trim()
        val projectBudget = etBudget.text.toString().toDoubleOrNull() ?: 0.0

        if (projectName.isNotEmpty() && projectBudget > 0) {
            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference("Budgets")
            var total = 0.0

            ref.orderByChild("projectName").equalTo(projectName)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // If the child node exists, update it
                            for (projectSnapshot in dataSnapshot.children) {
                                val budget = projectSnapshot.getValue(Budget::class.java)
                                if (budget != null) {
                                    budget.totalBudget = projectBudget
                                    val categories = ArrayList<Category>()

                                    // Update the category details
                                    val linearLayout =
                                        findViewById<LinearLayout>(R.id.linear_layout_cat)
                                    for (i in 0 until linearLayout.childCount) {
                                        val horizontalLay = linearLayout.getChildAt(i) as? LinearLayout
                                        val editTextViewName = horizontalLay?.getChildAt(0) as? EditText
                                        val editTextView =
                                            horizontalLay?.getChildAt(1) as? EditText

                                        if (editTextViewName != null && editTextView != null) {
                                            val categoryName =
                                                editTextViewName.text.toString().trim()
                                            val categoryAmount =
                                                editTextView.text.toString().toDoubleOrNull() ?: 0.0

                                            total += categoryAmount
                                            categories.add(Category(categoryName, categoryAmount))
                                        }
                                    }

                                    if (total > projectBudget){
                                        Toast.makeText(applicationContext, "Allocations are more than the Budget", Toast.LENGTH_LONG).show()
                                        return
                                    }

                                    budget.available = projectBudget - total
                                    budget.categories = categories

                                    // Update the record in the database
                                    projectSnapshot.ref.setValue(budget)

                                    Toast.makeText(applicationContext, "Budget Data Updated", Toast.LENGTH_LONG).show()
                                    val intent = Intent(applicationContext, BudgetFetchActivity::class.java)
                                    startActivity(intent)
                                    finish()
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
        } else {
            Toast.makeText(applicationContext, "Please enter valid details", Toast.LENGTH_LONG).show()
        }
    }

    //Deleting budget records
    private fun deleteRecord(id: String){
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("Budgets")

        // Find the child node with the projectName value
        ref.orderByChild("projectName").equalTo(id).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // If the child node exists, delete it
                    for (projectSnapshot in dataSnapshot.children) {
                        projectSnapshot.ref.removeValue()
                        Toast.makeText(applicationContext, "Budget Data Deleted", Toast.LENGTH_LONG).show()
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

        val intent = Intent(this, BudgetFetchActivity::class.java)
        finish()
        startActivity(intent)
    }
}

