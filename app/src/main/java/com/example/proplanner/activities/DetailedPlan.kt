//IT21313684
//Karunarathna D.T.S
package com.example.proplanner.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proplanner.R
import com.example.proplanner.databinding.ActivityDetailedPlanBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class DetailedPlan : AppCompatActivity() {
    //Update

    //Initialized
    private lateinit var binding: ActivityDetailedPlanBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout from activity_detailed_plan.xml and set as view
        binding = ActivityDetailedPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageButton: ImageButton = findViewById(R.id.imageButton)

        // Set click listener for the imageButton
        imageButton.setOnClickListener {
            // Start the PlanDashboard activity
            val intent = Intent(this, PlanDashboard::class.java)
            startActivity(intent)
        }

        // Get data from previous activity and set to corresponding views
        val bundle = intent.extras
        if (bundle != null){
            binding.detailPName.text = Editable.Factory.getInstance().newEditable(bundle.getString("planName"))
            binding.detailPDes.text = Editable.Factory.getInstance().newEditable(bundle.getString("planDesc"))
            binding.detailPPeriod.text = Editable.Factory.getInstance().newEditable(bundle.getString("planPeriod"))
            binding.detailPTask.text = Editable.Factory.getInstance().newEditable(bundle.getString("planTasks"))
        }

        binding.btnDelete.setOnClickListener{
            val nameplan = binding.detailPName.text.toString()
            if (nameplan.isNotEmpty())
                deleteData(nameplan)
            else
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }

        binding.updatebutton.setOnClickListener {
            val updateNameplan = binding.detailPName.text.toString()
            val updateDes = binding.detailPDes.text.toString()
            val updatePeriod = binding.detailPPeriod.text.toString()
            val updateTask = binding.detailPTask.text.toString()
            if (updateNameplan.isNotEmpty() && updateDes.isNotEmpty() && updatePeriod.isNotEmpty() && updateTask.isNotEmpty()) {
                updateData(updateNameplan, updateDes, updatePeriod, updateTask)
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
    // Function to delete plan data from Firebase database
    private fun deleteData(nameplan: String){
        database = FirebaseDatabase.getInstance().getReference("Plans")
        database.child(nameplan).removeValue().addOnSuccessListener {

            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()

            // Go back to PlanDashboard activity
            val intent = Intent(this@DetailedPlan, PlanDashboard::class.java)
            startActivity(intent)
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Unable to delete", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to update plan data in Firebase database
    private fun updateData( dataProName: String,dataProDesc: String, dataTPeriod: String, dataTask: String) {
        database = FirebaseDatabase.getInstance().getReference("Plans")

        // Create map of updated plan data
        val uplans = mapOf(

            "dataProName" to dataProName,
            "dataProDesc" to dataProDesc,
            "dataTPeriod" to dataTPeriod,
            "dataTask" to dataTask,

            )
        database.child(dataProName).updateChildren(uplans).addOnSuccessListener {

            // Update plan description view with new data
            val updatedDescription = StringBuilder(dataProDesc)
            binding.detailPDes.text = Editable.Factory.getInstance().newEditable(updatedDescription)

            Toast.makeText(this,"Successfully Updated",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@DetailedPlan, PlanDashboard::class.java)
            startActivity(intent)
            finish()
        }.addOnFailureListener{
            Toast.makeText(this,"Failed to Update",Toast.LENGTH_SHORT).show()
        }
    }

}