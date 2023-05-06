//IT21318320 - Silva T.U.D
package com.example.proplanner.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.example.proplanner.R
import com.example.proplanner.models.Budget
import com.google.firebase.database.*

class BudgetInsightActivity : AppCompatActivity() {
    //Declaring variables
    private lateinit var svTips : ScrollView
    private lateinit var ibBack : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_insight)

        //Initializing variables
        svTips = findViewById(R.id.svTips)
        ibBack = findViewById(R.id.ibBack)

        //Redirecting to budget home page on button click
        ibBack.setOnClickListener{
            var intent = Intent(this, BudgetHomeActivity::class.java)
            startActivity(intent)
        }

        //Fetch budget data
        getBudgetsData()
    }

    private fun getBudgetsData() {
        val dbRef = FirebaseDatabase.getInstance().getReference("Budgets")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val container = LinearLayout(this@BudgetInsightActivity)
                container.orientation = LinearLayout.VERTICAL

                val budgetList = mutableListOf<Budget>()
                if (snapshot.exists()) {
                    for (budgetSnap in snapshot.children) {
                        val budgetData = budgetSnap.getValue(Budget::class.java)
                        budgetData?.let {
                            budgetList.add(it)
                        }
                    }

                    for (budget in budgetList) {
                        val spent = budget.totalBudget!! - budget.available!!
                        val linearLayout = LinearLayout(this@BudgetInsightActivity)
                        linearLayout.orientation = LinearLayout.VERTICAL

                        //Display budgets according to budget insight
                        if(spent < budget.available!!){
                            linearLayout.setBackgroundResource(R.drawable.button_background)
                        }else{
                            linearLayout.setBackgroundResource(R.drawable.background_btn_delete)
                        }

                        // Convert dp to pixels
                        val displayMetrics = resources.displayMetrics
                        val widthInDp = 380 // width in dp
                        val heightInDp = 150 // height in dp
                        val widthInPx = (widthInDp * displayMetrics.density).toInt()
                        val heightInPx = (heightInDp * displayMetrics.density).toInt()

                        // Set width and height
                        val params = LinearLayout.LayoutParams(
                            widthInPx,
                            heightInPx
                        )

                        // Set margins
                        params.setMargins(35, 30, 16, 16)

                        linearLayout.layoutParams = params

                        val tvTips = TextView(this@BudgetInsightActivity)
                        tvTips.text = "${budget.projectName}\n\nAvailable budget : ${budget.available}\n\nSpent : $spent"
                        tvTips.textSize = 20F
                        tvTips.setTextColor(Color.WHITE)
                        val tvParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        val tvLeftMargin = 30
                        val tvTopMargin = 30
                        val tvRightMargin = 16
                        val tvBottomMargin = 30
                        tvParams.setMargins(tvLeftMargin, tvTopMargin, tvRightMargin, tvBottomMargin)
                        tvTips.layoutParams = tvParams
                        linearLayout.addView(tvTips)
                        container.addView(linearLayout)
                    }
                    svTips.addView(container)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                println("Error: ${error.message}")
            }
        })
    }
}