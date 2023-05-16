//IT21321504
//Gunawardana N.B.C.A.W.

package com.example.proplanner.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.proplanner.R

class SalesWelcome : AppCompatActivity() {

    private lateinit var txtSkip : TextView
    private lateinit var textView5: TextView




    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_welcome)

        txtSkip = findViewById(R.id.tskip)
        textView5 = findViewById(R.id.textView5)

        txtSkip.setOnClickListener{
            var intent = Intent(this, MainDashboard::class.java)
            startActivity(intent)
        }

        textView5.setOnClickListener {
            val intent = Intent(this, MainDashboard::class.java)
            startActivity(intent)
        }
    }
}