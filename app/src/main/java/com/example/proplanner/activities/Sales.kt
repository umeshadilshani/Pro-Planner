//IT21321504
//Gunawardana N.B.C.A.W.

package com.example.proplanner.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proplanner.R
import com.example.proplanner.adapters.SalesAdapter
import com.example.proplanner.databinding.ActivitySalesBinding
import com.example.proplanner.models.SalesModel


import com.google.firebase.database.*


class Sales : AppCompatActivity() {
    private lateinit var binding: ActivitySalesBinding
    private lateinit var dataList: ArrayList<SalesModel>
    private lateinit var adapter: SalesAdapter
    var databaseReference:DatabaseReference? =null
    var eventListener:ValueEventListener? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using view binding
        binding = ActivitySalesBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val imageButton: ImageButton = findViewById(R.id.ibSalesBack)

        // Set click listener for the imageButton
        imageButton.setOnClickListener {
            // Start the SalesDashboard activity
            val intent = Intent(this, SalesDashboard::class.java)
            startActivity(intent)
        }

// Set up the grid layout manager for the RecyclerView
        val gridLayoutManager = GridLayoutManager(this@Sales, 1)
        binding.recyclerView.layoutManager = gridLayoutManager

        val builder = AlertDialog.Builder(this@Sales)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()


        // Initialize the sales data list and adapter
        dataList = ArrayList()
        adapter = SalesAdapter(this@Sales, dataList)
        binding.recyclerView.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("Sales")
        dialog.show()


        // Add a ValueEventListener to the database reference to retrieve the sales data
        // onDataChange method called when the data at the database reference changes
        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()

                // Loop through the children of the database reference to get the sales data
                for (itemSnapshot in snapshot.children) {
                    val salesModel = itemSnapshot.getValue(SalesModel::class.java)
                    if (salesModel != null) {
                        dataList.add(salesModel)
                    }
                }
                // Notify the adapter that the data set has changed
                // Dismiss the dialog
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            // onCancelled method called if the database reference is cancelled
            // Dismiss the dialog
            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }

        })
    }
}