//IT21313684
//Karunarathna D.T.S

package com.example.proplanner.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proplanner.databinding.ActivityCreatePlanBinding
import com.example.proplanner.models.DataClass
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreatePlan : AppCompatActivity() {

    // Declare the binding variable and database reference variable
    lateinit var binding: ActivityCreatePlanBinding
    private lateinit var databaseReference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using the binding variable
        binding = ActivityCreatePlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("Plans")

        binding.saveBtn.setOnClickListener{
            if(validateInputs()){
                val pName = binding.projectName.text.toString().trim()
                val pDescription = binding.projectDescription.text.toString().trim()
                val pTimePeriod = binding.timePeriod.text.toString().trim()
                val pTask = binding.task.text.toString().trim()

                // Create a DataClass object with the retrieved data
                val plans = DataClass(pName, pDescription, pTimePeriod, pTask)

                // Save the data to the database and handle success/failure
                databaseReference.child(pName).setValue(plans).addOnSuccessListener{
                    Toast.makeText(this, "Saved Successfully!", Toast.LENGTH_SHORT).show()
                    clearInputs()
                    startDashboard()
                }.addOnFailureListener{ e->
                    Toast.makeText(this@CreatePlan, "Failed to save: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    //
    fun validateInputs(): Boolean {
        var isValid = true

        // validate project name
        val pName = binding.projectName.text.toString().trim()
        if(pName.isEmpty()){
            binding.projectName.error = "Project name is required!"
            isValid = false
        }

        // validate project description
        val pDescription = binding.projectDescription.text.toString().trim()
        if(pDescription.isEmpty()){
            binding.projectDescription.error = "Project description is required!"
            isValid = false
        }

        // validate time period
        val pTimePeriod = binding.timePeriod.text.toString().trim()
        if(pTimePeriod.isEmpty()){
            binding.timePeriod.error = "Time period is required!"
            isValid = false
        }

        // validate task
        val pTask = binding.task.text.toString().trim()
        if(pTask.isEmpty()){
            binding.task.error = "Task is required!"
            isValid = false
        }

        return isValid
    }

    private fun clearInputs() {
        binding.projectName.text.clear()
        binding.projectDescription.text.clear()
        binding.timePeriod.text.clear()
        binding.task.text.clear()
    }

    private fun startDashboard() {
        val intent = Intent(this@CreatePlan, PlanDashboard::class.java)
        startActivity(intent)
        finish()
    }

}