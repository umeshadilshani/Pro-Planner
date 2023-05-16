//IT21313684
//Karunarathna D.T.S
package com.example.proplanner.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proplanner.R

class PlanWelcome : AppCompatActivity() {

    private lateinit var textView5: TextView
    private lateinit var txtSkip : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_welcome)

        textView5 = findViewById(R.id.textView5)
        txtSkip = findViewById(R.id.textView4)

        textView5.setOnClickListener {
            val intent = Intent(this, InventoryWelcome::class.java)
            startActivity(intent)
        }

        txtSkip.setOnClickListener{
            var intent = Intent(this, MainDashboard::class.java)
            startActivity(intent)
        }
    }
}