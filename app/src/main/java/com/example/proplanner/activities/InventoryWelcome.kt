//it21276996
//Fernando.W.Y.M
package com.example.proplanner.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proplanner.R

class InventoryWelcome : AppCompatActivity() {

    private lateinit var textView5: TextView
    private lateinit var txtSkip : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_welcome)

        textView5 = findViewById(R.id.textView5)
        txtSkip = findViewById(R.id.textView4)

        textView5.setOnClickListener {
            val intent = Intent(this, BudgetWelcomeActivity::class.java)
            startActivity(intent)
        }
        txtSkip.setOnClickListener{
            var intent = Intent(this, MainDashboard::class.java)
            startActivity(intent)
        }
    }
}