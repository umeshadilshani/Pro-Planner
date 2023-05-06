//IT21318320 - Silva T.U.D
package com.example.proplanner.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.example.proplanner.models.Category
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import com.example.proplanner.R
import com.example.proplanner.models.Budget
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class BudgetInsertionActivity : AppCompatActivity() {
    // Related to Add Categories
    lateinit var categoriesLayout :LinearLayout
    private lateinit var ibBackInsertB : ImageButton

    //Related to Add Budgets
    lateinit var etProjectName : EditText
    lateinit var etProjectBudget : EditText
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_insertion)

        // Related to Add Categories
        categoriesLayout = findViewById(R.id.categories_layout)

        //Related to Add Budgets
        etProjectName = findViewById(R.id.etProjectName)
        etProjectBudget = findViewById(R.id.etProjectBudget)
        database = FirebaseDatabase.getInstance().reference
        ibBackInsertB = findViewById(R.id.ibBackInsertB)

        ibBackInsertB.setOnClickListener{
            var intent = Intent(this, BudgetHomeActivity::class.java)
            startActivity(intent)
        }
    }

    // Show category form on button click
    fun addCategoryClicked(view: View) {
        val projectName = etProjectName.text.toString().trim()
        val projectBudget = etProjectBudget.text.toString().trim()

        if(TextUtils.isEmpty(projectName) || TextUtils.isEmpty(projectBudget)){
            Toast.makeText(this, "Please enter project name and budget", Toast.LENGTH_SHORT).show()
            return
        }

        if (categoriesLayout.childCount === 0){
            val inflater = LayoutInflater.from(this)
            val categoryView = inflater.inflate(R.layout.category_item, null, false)
            categoriesLayout.addView(categoryView)
        }else{
            val projectBudget = etProjectBudget.text.toString().trim()
            val projectTotalBudget = projectBudget.toDouble()
            var estimatedTotal = 0.0

            for(i in 0 until categoriesLayout.childCount){
                val categoryView =categoriesLayout.getChildAt(i)
                val etCatName = categoryView.findViewById<EditText>(R.id.etCatName)
                val etCatBudget = categoryView.findViewById<EditText>(R.id.etCatBudget)

                val catName = etCatName.text.toString().trim()
                val catBudget = etCatBudget.text.toString().trim()

                if (TextUtils.isEmpty(catName)) {
                    Toast.makeText(this, "Please enter category name", Toast.LENGTH_SHORT).show()
                    return
                }

                if (TextUtils.isEmpty(catBudget)) {
                    Toast.makeText(this, "Please enter budget for category", Toast.LENGTH_SHORT).show()
                    return
                }

                val catAmount = catBudget.toDouble()

                estimatedTotal +=  catAmount
            }
            if (estimatedTotal > projectTotalBudget) {
                Toast.makeText(this, "Estimated amounts are greater than allocated budget", Toast.LENGTH_SHORT).show()
                return
            }else if (estimatedTotal === projectTotalBudget) {
                Toast.makeText(this, "Estimated amounts is equal to allocated budget", Toast.LENGTH_SHORT).show()
                return
            }else{
                val inflater = LayoutInflater.from(this)
                val categoryView = inflater.inflate(R.layout.category_item, null, false)
                categoriesLayout.addView(categoryView)
            }
        }
    }

    // Save budgets on button click
    fun saveBudgetClicked(view: View){
        val projectName = etProjectName.text.toString().trim()
        val projectBudget = etProjectBudget.text.toString().trim()

        if(TextUtils.isEmpty(projectName)){
            Toast.makeText(this, "Please enter project name", Toast.LENGTH_SHORT).show()
            return
        }

        if(TextUtils.isEmpty(projectBudget)){
            Toast.makeText(this, "Please enter project budget", Toast.LENGTH_SHORT).show()
            return
        }

        val projectTotalBudget = projectBudget.toDouble()
        var estimatedTotal = 0.0

        val categories =ArrayList<Category>()

        for(i in 0 until categoriesLayout.childCount){
            val categoryView =categoriesLayout.getChildAt(i)
            val etCatName = categoryView.findViewById<EditText>(R.id.etCatName)
            val etCatBudget = categoryView.findViewById<EditText>(R.id.etCatBudget)

            val catName = etCatName.text.toString().trim()
            val catBudget = etCatBudget.text.toString().trim()

            if (TextUtils.isEmpty(catName)) {
                Toast.makeText(this, "Please enter category name", Toast.LENGTH_SHORT).show()
                return
            }

            if (TextUtils.isEmpty(catBudget)) {
                Toast.makeText(this, "Please enter budget for category", Toast.LENGTH_SHORT).show()
                return
            }

            val catAmount = catBudget.toDouble()
            estimatedTotal +=  catAmount

            if(estimatedTotal > projectTotalBudget){
                Toast.makeText(this, "Estimated amounts are greater than allocated budget", Toast.LENGTH_SHORT).show()
                return
            }

            categories.add(Category(catName, catAmount))
        }

        val availableBudget = projectTotalBudget - estimatedTotal;

        val budget = Budget(projectName, projectTotalBudget,availableBudget, categories)

        val budgetId = database.child("Budgets").push().key

        database.child("Budgets").child(budgetId!!).setValue(budget)
            .addOnSuccessListener {
                Toast.makeText(this, "Budget saved successfully", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, BudgetFetchActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener{
                Toast.makeText(this, "Failed to save budget", Toast.LENGTH_SHORT).show()
            }
    }
}