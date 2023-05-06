//IT21321504
//Gunawardana N.B.C.A.W.
package com.example.proplanner.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.ImageButton
import android.widget.Toast
import com.example.proplanner.R
import com.example.proplanner.databinding.ActivityAddSalesBinding

import com.example.proplanner.models.SalesModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddSales : AppCompatActivity() {

    // Declare variables for the UI elements and database reference

    lateinit var binding: ActivityAddSalesBinding
    private lateinit var databaseReference : DatabaseReference

    private lateinit var btnAback : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSalesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnAback = findViewById(R.id.btnSdback)

        btnAback.setOnClickListener {
            var intent = Intent(this, SalesDashboard::class.java)
            startActivity(intent)
        }

        // Set a click listener for the "Add" button
        binding.btnAdd.setOnClickListener {
            if (validateFields()) {
                // Get a reference to the Firebase Realtime Database
                // Create a new SalesModel object with the values entered by the user
                databaseReference = FirebaseDatabase.getInstance().getReference("Sales")
                val product = SalesModel(
                    binding.AprName.text.toString(),
                    binding.AprBatchNo.text.toString(),
                    binding.AprQuan.text.toString(),
                    binding.AprUniPri.text.toString(),
                    binding.AprDate.text.toString()
                )

                databaseReference.child(binding.AprName.text.toString()).setValue(product)
                    .addOnSuccessListener {

                        Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@AddSales, SalesDashboard::class.java)
                        startActivity(intent)
                        finish()
                    }.addOnFailureListener { e ->
                        Toast.makeText(this@AddSales, e.message.toString(), Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    // Validate the input fields
    fun validateFields(): Boolean {
        var isValid = true

        if (binding.AprName.text.toString().isEmpty()) {
            isValid = false
            binding.AprName.error = "Please enter a product name"
        }

        if (binding.AprBatchNo.text.toString().isEmpty()) {
            isValid = false
            binding.AprBatchNo.error = "Please enter a batch number"
        }

        if (binding.AprQuan.text.toString().isEmpty()) {
            isValid = false
            binding.AprQuan.error = "Please enter a quantity"
        }

        if (binding.AprUniPri.text.toString().isEmpty()) {
            isValid = false
            binding.AprUniPri.error = "Please enter a unit price"
        }

        if (binding.AprDate.text.toString().isEmpty()) {
            isValid = false
            binding.AprDate.error = "Please enter a date"
        }

        return isValid
    }
}