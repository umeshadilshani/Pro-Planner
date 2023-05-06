//IT21318320 - Silva T.U.D
package com.example.proplanner.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.proplanner.R

class BudgetWelcomeActivity : AppCompatActivity() {
    //Declaring variables
    private lateinit var txtSkip : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_welcome)

        //Initializing variables
        txtSkip = findViewById(R.id.tvSkip)

        //Redirecting to home page on button click
        txtSkip.setOnClickListener{
            var intent = Intent(this, BudgetHomeActivity::class.java)
            startActivity(intent)
        }
    }
}