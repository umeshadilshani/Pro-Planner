package com.example.proplanner.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proplanner.R

class MainDashboard : AppCompatActivity() {
    private lateinit var btnMyPlans : Button
    private lateinit var btnInventory : Button
    private lateinit var btnMyBudgets : Button
    private lateinit var btnSales : Button
    private lateinit var textView9: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dashboard)

        btnMyPlans = findViewById(R.id.btnMyPlans)
        btnInventory = findViewById(R.id.btnInventory)
        btnMyBudgets = findViewById(R.id.btnMyBudgets)
        btnSales = findViewById(R.id.btnSales)
        textView9 = findViewById(R.id.textView9)

        btnMyPlans.setOnClickListener{
            var intent = Intent(this@MainDashboard, PlanDashboard::class.java)
            startActivity(intent)
        }

        btnMyBudgets.setOnClickListener{
            var intent = Intent(this@MainDashboard, BudgetHomeActivity::class.java)
            startActivity(intent)
        }

        btnInventory.setOnClickListener{
            var intent = Intent(this@MainDashboard, InventoryDashboard::class.java)
            startActivity(intent)
        }

        btnSales.setOnClickListener{
            var intent = Intent(this@MainDashboard, SalesDashboard::class.java)
            startActivity(intent)
        }
        textView9.setOnClickListener{
            openPlanWelcome()
        }


    }
    fun openPlanWelcome() {
        val intent = Intent(this, PlanWelcome::class.java)
        startActivity(intent)
    }
}