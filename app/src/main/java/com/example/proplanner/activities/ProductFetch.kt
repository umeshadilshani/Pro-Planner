//it21276996
//Fernando.W.Y.M
package com.example.proplanner.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.proplanner.R
import com.example.proplanner.adapters.ProductAdapter
import com.example.proplanner.models.ProductModel
import com.example.proplanner.activities.EditProduct

import com.google.firebase.database.*

class ProductFetch : AppCompatActivity() {
    // Define the RecyclerView, loading text view, product list, and database reference
    private lateinit var proRecyclerView : RecyclerView
    private lateinit var tvLoadingData : TextView
    private lateinit var proList: ArrayList<ProductModel>
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_fetch)

        proRecyclerView = findViewById(R.id.rvProduct)
        proRecyclerView.layoutManager = LinearLayoutManager(this)
        proRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        proList = arrayListOf<ProductModel>()

        getProductData()
    }

    // Define a function to fetch the product data from the Firebase database
    private fun getProductData(){
        proRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Product")

        // Add a listener to fetch the product data from the database
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                proList.clear()
                if(snapshot.exists()){
                    for(proSnap in snapshot.children){
                        val proData = proSnap.getValue(ProductModel::class.java)
                        proList.add(proData!!)
                    }

                    val mAdapter = ProductAdapter(proList)
                    proRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : ProductAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent= Intent(this@ProductFetch, EditProduct::class.java)

                            intent.putExtra("proid", proList[position].proid)
                            intent.putExtra("ptproname", proList[position].ptproname)
                            intent.putExtra("ptquan", proList[position].ptquan)
                            intent.putExtra("ptsupname", proList[position].ptsupname)
                            intent.putExtra("ptpri", proList[position].ptpri)
                            startActivity(intent)
                        }

                    })

                    proRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}