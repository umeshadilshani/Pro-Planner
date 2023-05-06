//IT21321504
//Gunawardana N.B.C.A.W.

package com.example.proplanner.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.proplanner.R
import com.example.proplanner.databinding.ActivitySalesDashboardBinding
import com.google.firebase.database.DatabaseReference

class SalesDashboard : AppCompatActivity() {

    private lateinit var btnSalesD : TextView
    private lateinit var  viewSale: TextView

    // The method is marked with @SuppressLint("MissingInflatedId") because the TextViews
    // don't have an ID specified in the layout file, but we will initialize them later
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_dashboard)
        // Add a click listener to "btnSalesD"
        btnSalesD = findViewById(R.id.btnSalesD)

        btnSalesD.setOnClickListener{
            var intent = Intent(this, AddSales::class.java)
            startActivity(intent)
        }

        val imageButton: ImageButton = findViewById(R.id.btnSdback)

        // Set click listener for the imageButton
        imageButton.setOnClickListener {
            // Start the SalesDashboard activity
            val intent = Intent(this, MainDashboard::class.java)
            startActivity(intent)
        }

        // Add a click listener to "viewSale"
        viewSale = findViewById(R.id.viewSale)

        viewSale.setOnClickListener{
            var intent = Intent(this, Sales::class.java)
            startActivity(intent)
        }


    }
}