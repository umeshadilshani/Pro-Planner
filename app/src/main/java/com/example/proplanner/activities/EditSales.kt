//IT21321504
//Gunawardana N.B.C.A.W.

package com.example.proplanner.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.ImageButton
import android.widget.Toast
import com.example.proplanner.R
import com.example.proplanner.databinding.ActivityEditSalesBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditSales : AppCompatActivity() {

    // Initialize variables
    private lateinit var binding: ActivityEditSalesBinding
    private lateinit var database: DatabaseReference

    private lateinit var btnSback : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEditSalesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        btnSback = findViewById(R.id.btnSdback)

        btnSback.setOnClickListener{
            var intent = Intent(this, Sales::class.java)
            startActivity(intent)
        }


        // Retrieve data passed from previous activity using intent extras
        val bundle = intent.extras
        if (bundle != null){
            binding.prName.text = Editable.Factory.getInstance().newEditable(bundle.getString("productName"))
            binding.prBatchNo.text =Editable.Factory.getInstance().newEditable(bundle.getString("productBatchNo"))
            binding.prQuan.text =Editable.Factory.getInstance().newEditable( bundle.getString("productQuantity"))
            binding.prUniPri.text =Editable.Factory.getInstance().newEditable(bundle.getString("productUnitPrice"))
            binding.prDate.text =Editable.Factory.getInstance().newEditable(bundle.getString("productDate"))
        }
        // Set onClickListener for delete button
        binding.btndelete.setOnClickListener{
            val nameproduct = binding.prName.text.toString()
            if (nameproduct.isNotEmpty())
                deleteData(nameproduct)
            else
                Toast.makeText(this, "Product name cannot be empty", Toast.LENGTH_SHORT).show()
        }
        // Set onClickListener for update button
        binding.btnupdate.setOnClickListener {
            val updateName = binding.prName.text.toString()
            val updateBtNo = binding.prBatchNo.text.toString()
            val updateQuan = binding.prQuan.text.toString()
            val updateUP = binding.prUniPri.text.toString()
            val upDt = binding.prDate.text.toString()

            // Validate inputs
            if (updateName.isEmpty()) {
                Toast.makeText(this, "Product name cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (updateBtNo.isEmpty()) {
                Toast.makeText(this, "Batch number cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (updateQuan.isEmpty()) {
                Toast.makeText(this, "Product quantity must be a positive integer", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (updateUP.isEmpty()) {
                Toast.makeText(this, "Product unit price must be a positive number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (upDt.isEmpty()) {
                Toast.makeText(this, "Date cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Call updateData function with retrieved values as parameters
            updateData(updateName,updateBtNo,updateQuan,updateUP,upDt)
        }
    }
    // Function to delete data from the Firebase Realtime Database
    private fun deleteData(nameproduct: String){
        database = FirebaseDatabase.getInstance().getReference("Sales")
        database.child(nameproduct).removeValue().addOnSuccessListener {

            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@EditSales, Sales::class.java)
            startActivity(intent)
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Unable to delete", Toast.LENGTH_SHORT).show()
        }
    }
    // Function to update data in the Firebase Realtime Database
    private fun updateData(
        prName: String,
        prBatchNo: String,
        prQuan: String,
        prUniPri: String,
        upDt: String
    ) {
        database = FirebaseDatabase.getInstance().getReference("Sales")
        val uplans = mapOf(

            "prName" to prName,
            "prBatchNo" to prBatchNo,
            "prQuan" to prQuan,
            "prUniPri" to prUniPri,
            "prDate" to upDt,

            )

        database.child(prName).updateChildren(uplans).addOnSuccessListener {

            val updatedBatchNo = StringBuilder(prBatchNo)
            binding.prBatchNo.text = Editable.Factory.getInstance().newEditable(updatedBatchNo.toString())

            Toast.makeText(this,"Successfully Updated", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@EditSales, Sales::class.java)
            startActivity(intent)
            finish()
        }.addOnFailureListener{
            Toast.makeText(this,"Failed to Update", Toast.LENGTH_SHORT).show()
        }

    }
}