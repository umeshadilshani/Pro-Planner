//IT21313684
//Karunarathna D.T.S
package com.example.proplanner.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proplanner.adapters.PlanAdapter
import com.example.proplanner.databinding.ActivityPlanDashboardBinding
import com.example.proplanner.models.DataClass
import com.google.firebase.database.*

class PlanDashboard : AppCompatActivity() {

    //added
    // Declare variables
    private lateinit var binding: ActivityPlanDashboardBinding
    private lateinit var dataList: ArrayList<DataClass>
    private lateinit var adapter: PlanAdapter
    var databaseReference: DatabaseReference? =null
    var eventListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //view
        // Set up RecyclerView with GridLayoutManager
        val gridLayoutManager = GridLayoutManager(this@PlanDashboard, 1)
        binding.recyclerView.layoutManager = gridLayoutManager

        val builder = AlertDialog.Builder(this@PlanDashboard)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()

        dataList = ArrayList()
        adapter = PlanAdapter(this@PlanDashboard, dataList)
        binding.recyclerView.adapter = adapter

        // Retrieve data from Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Plans")
        dialog.show()

        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(DataClass::class.java)
                    if (dataClass != null) {
                        dataList.add(dataClass)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }
        })

        binding.imageButton.setOnClickListener {
            val intent = Intent(this, MainDashboard::class.java)
            startActivity(intent)
            finish()
        }



        // Set onClickListener for Create Plan button
        binding.planCreatBtn.setOnClickListener{
            val intent = Intent(this, CreatePlan::class.java)
            startActivity(intent)
            finish()
        }
    }
}