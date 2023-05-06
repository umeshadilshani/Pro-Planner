//IT21318320 - Silva T.U.D
package com.example.proplanner.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.proplanner.R
import com.google.firebase.database.*

class BudgetHomeActivity : AppCompatActivity() {
    //Declaring variables
    private lateinit var tvCreate : TextView
    private lateinit var ibCreate : ImageButton
    private lateinit var cvCreate : CardView
    private lateinit var tvView : TextView
    private lateinit var ibView : ImageButton
    private lateinit var cvView : CardView
    private lateinit var tvInsight : TextView
    private lateinit var ibInsight : ImageButton
    private lateinit var cvInsight : CardView
    private lateinit var tvFullBudget : TextView
    private lateinit var tvFullSpent : TextView
    private lateinit var tvFullAvailable : TextView
    private lateinit var tvTipsI : TextView
    private lateinit var ibTipsI : ImageButton
    private lateinit var cvTipsI : CardView
    private lateinit var btnBack : ImageButton
//    val imageButton: ImageButton = findViewById(R.id.ibBackHome)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_home)

        //Initializing variables
        tvCreate = findViewById(R.id.tvCreateBudget)
        ibCreate = findViewById(R.id.ibCreateBudget)
        cvCreate = findViewById(R.id.cvCreateBudget)

        tvView = findViewById(R.id.tvViewBudget)
        ibView = findViewById(R.id.ibViewBudget)
        cvView = findViewById(R.id.cvViewBudget)

        tvInsight = findViewById(R.id.tvInsight)
        ibInsight = findViewById(R.id.ibInsight)
        cvInsight = findViewById(R.id.cvInsight)

        tvTipsI = findViewById(R.id.tvTipsI)
        ibTipsI = findViewById(R.id.ibTipsI)
        cvTipsI = findViewById(R.id.cvTipsI)

        tvFullBudget = findViewById(R.id.tvFullAmount)
        tvFullSpent = findViewById(R.id.tvFullSpent)
        tvFullAvailable = findViewById(R.id.tvFullAvailable)

        btnBack = findViewById(R.id.ibBackHome)

        btnBack.setOnClickListener{
            var intent = Intent(this@BudgetHomeActivity, MainDashboard::class.java)
            startActivity(intent)
        }

        //Redirecting to activities on button click
        tvCreate.setOnClickListener{
            navigateCreateBudget()
        }

        ibCreate.setOnClickListener{
            navigateCreateBudget()
        }

        cvCreate.setOnClickListener{
            navigateCreateBudget()
        }

        tvView.setOnClickListener{
            navigateViewBudget()
        }

        ibView.setOnClickListener{
            navigateViewBudget()
        }

        cvView.setOnClickListener{
            navigateViewBudget()
        }

        tvInsight.setOnClickListener{
            navigateBudgetInsight()
        }

        ibInsight.setOnClickListener{
            navigateBudgetInsight()
        }

        cvInsight.setOnClickListener{
            navigateBudgetInsight()
        }

        tvTipsI.setOnClickListener{
            navigateTips()
        }

        ibTipsI.setOnClickListener{
            navigateTips()
        }

        cvTipsI.setOnClickListener{
            navigateTips()
        }


        // Set click listener for the imageButton
//        imageButton.setOnClickListener {
//            // Start the SalesDashboard activity
//            val intent = Intent(this, MainDashboard::class.java)
//            startActivity(intent)
//        }

        //Calculating and displaying overall budget records
        val database = FirebaseDatabase.getInstance().getReference("Budgets")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var totalBudget = 0.0
                var totalAvailable = 0.0
                for (snapshot in dataSnapshot.children) {
                    val budget = snapshot.child("totalBudget").getValue(Double::class.java) ?: 0.0
                    val available = snapshot.child("available").getValue(Double::class.java) ?: 0.0
                    totalBudget += budget
                    totalAvailable += available
                }

                var spent = totalBudget - totalAvailable
                tvFullBudget.text = "Rs. $totalBudget"
                tvFullAvailable.text = "Rs. $totalAvailable"
                tvFullSpent.text = "Rs. $spent"
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun navigateCreateBudget(){
        var intent = Intent(this, BudgetInsertionActivity::class.java)
        startActivity(intent)
    }

    private fun navigateViewBudget(){
        var intent = Intent(this, BudgetFetchActivity::class.java)
        startActivity(intent)
    }

    private fun navigateBudgetInsight(){
        var intent = Intent(this, BudgetInsightActivity::class.java)
        startActivity(intent)
    }

    private fun navigateTips(){
        var intent = Intent(this, BudgetTipsActivity::class.java)
        startActivity(intent)
    }
}