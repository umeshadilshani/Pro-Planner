//IT21318320 - Silva T.U.D
package com.example.proplanner.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proplanner.R
import com.example.proplanner.adapters.BudgetAdapter
import com.example.proplanner.models.Budget
import com.example.proplanner.models.Category
import com.google.firebase.database.*

class BudgetFetchActivity : AppCompatActivity() {
    //Declaring variables
    private lateinit var budgetsRecyclerView : RecyclerView
    private lateinit var tvLoadingData : TextView
    private lateinit var budgetList: ArrayList<Budget>
    private lateinit var dbRef : DatabaseReference
    private lateinit var etSearch : EditText
    private lateinit var ivSearch : ImageView
    private lateinit var ibBack : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_fetch)

        //Initializing variables
        budgetsRecyclerView = findViewById(R.id.rvBudgets)
        budgetsRecyclerView.layoutManager = LinearLayoutManager(this)
        budgetsRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)
        etSearch = findViewById(R.id.etSearch)
        ivSearch = findViewById(R.id.ivSearchIcon)
        ibBack = findViewById(R.id.ibBack)

        budgetList = arrayListOf<Budget>()

        //Fetching all budget records
        getBudgetsData()

        //Redirecting to homa page
        ibBack.setOnClickListener{
            var intent = Intent(this, BudgetHomeActivity::class.java)
            startActivity(intent)
        }

        //Search budget records
        ivSearch.setOnClickListener{
            //Accessing project name
            var searchPrjName = etSearch.text.toString().trim()
            var found = false

                val dbRef = FirebaseDatabase.getInstance().getReference("Budgets")

                dbRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val budgetList = mutableListOf<Budget>()
                        if (snapshot.exists()) {
                            //Accessing required record
                            for (budgetSnap in snapshot.children) {
                                val budgetData = budgetSnap.getValue(Budget::class.java)
                                budgetData?.let {
                                    budgetList.add(it)
                                }
                            }

                            //Passing record details
                            for (budget in budgetList) {
                                if(budget.projectName == searchPrjName) {
                                    val intent = Intent(this@BudgetFetchActivity, BudgetDetailsActivity::class.java)
                                    intent.putExtra("projectName", budget.projectName)
                                    intent.putExtra("projectBudget", budget.totalBudget)
                                    intent.putExtra("projectAvailable", budget.available)
                                    var categories: ArrayList<Category> ?= ArrayList()
                                    categories = budget.categories

                                    if(categories == null){
                                        intent.putParcelableArrayListExtra("categories", ArrayList())
                                    }else{
                                        intent.putParcelableArrayListExtra("categories", ArrayList(categories))
                                    }

                                    found = true

                                    startActivity(intent)
                                }
                            }
                            if(!found){
                                Toast.makeText(this@BudgetFetchActivity, "Project Not Found", Toast.LENGTH_LONG).show()
                                etSearch.text.clear()
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle error
                        println("Error: ${error.message}")
                    }
                })
            }
        }

    //Fetch all budget records
    private fun getBudgetsData(){
        budgetsRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Budgets")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                budgetList.clear()
                if(snapshot.exists()){
                    for(budgetSnap in snapshot.children){
                        val budgetData = budgetSnap.getValue(Budget::class.java)
                        budgetList.add(budgetData!!)
                    }

                    val mAdapter = BudgetAdapter(budgetList)
                    budgetsRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : BudgetAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@BudgetFetchActivity, BudgetDetailsActivity::class.java)
                            intent.putExtra("projectName", budgetList[position].projectName)
                            intent.putExtra("projectBudget", budgetList[position].totalBudget)
                            intent.putExtra("projectAvailable", budgetList[position].available)
                            var categories: ArrayList<Category> ?= ArrayList()
                            categories = budgetList[position].categories

                            if(categories == null){
                                intent.putParcelableArrayListExtra("categories", ArrayList())
                            }else{
                                intent.putParcelableArrayListExtra("categories", ArrayList(categories))
                            }

                            startActivity(intent)
                        }
                    })

                    budgetsRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}