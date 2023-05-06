//it21276996
//Fernando.W.Y.M
package com.example.proplanner.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.proplanner.R

class InventoryDashboard : AppCompatActivity() {

    // Declare variables for image buttons
    private lateinit var imageButton2 : ImageButton
    private lateinit var imageButton : ImageButton


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_dashboard)

        // Initialize image buttons
        imageButton2 = findViewById(R.id.imageButton2)
        imageButton = findViewById(R.id.imageButton)



        imageButton2.setOnClickListener {
            val intent = Intent ( this, AddProduct::class.java)
            startActivity(intent)
        }
        imageButton.setOnClickListener{
            val intent = Intent(this, ProductFetch::class.java);
            startActivity(intent)
        }

        val imageButton: ImageButton = findViewById(R.id.btnProback)

        // Set click listener for the imageButton
        imageButton.setOnClickListener {
            // Start the InventoryDashboard activity
            val intent = Intent(this, MainDashboard::class.java)
            startActivity(intent)
        }
    }
}