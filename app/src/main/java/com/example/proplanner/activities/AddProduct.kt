//it21276996
//Fernando.W.Y.M

package com.example.proplanner.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.proplanner.R
import com.example.proplanner.models.ProductModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddProduct : AppCompatActivity() {

    // Declaring UI elements
    lateinit var ptProName: EditText
    lateinit var ptQuan: EditText
    lateinit var ptSupName: EditText
    lateinit var ptPri: EditText
    private lateinit var button2: Button
    private lateinit var imageButton7: ImageButton


    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        // Initializing UI elements
        ptProName = findViewById(R.id.ptProName)
        ptQuan = findViewById(R.id.ptQuan)
        ptSupName = findViewById(R.id.ptSupName)
        ptPri = findViewById(R.id.ptPri)
        button2 = findViewById(R.id.button2)
        imageButton7 = findViewById(R.id.imageButton7)

        dbRef = FirebaseDatabase.getInstance().getReference("Product")

        button2.setOnClickListener {
            saveProductData()
        }

        imageButton7.setOnClickListener {
            val intent = Intent(this, InventoryDashboard::class.java)
            startActivity(intent)
        }
    }

    // Function to validate inputs
    fun validateInputs(): Boolean {
        var isValid = true

        if (ptProName.text.isNullOrEmpty()) {
            ptProName.error = "Product name cannot be empty"
            isValid = false
        }

        if (ptQuan.text.isNullOrEmpty()) {
            ptQuan.error = "Quantity cannot be empty"
            isValid = false
        }

        if (ptSupName.text.isNullOrEmpty()) {
            ptSupName.error = "Supplier name cannot be empty"
            isValid = false
        }

        if (ptPri.text.isNullOrEmpty()) {
            ptPri.error = "Price cannot be empty"
            isValid = false
        }

        return isValid
    }

    // Function to save product data to the database
    private fun saveProductData() {
        if (!validateInputs()) {
            return
        }
        //getting values
        val ptproname = ptProName.text.toString()
        val ptquan = ptQuan.text.toString()
        val ptsupname = ptSupName.text.toString()
        val ptpri = ptPri.text.toString()


        if (ptproname.isEmpty()) {
            ptProName.error = "Product Name"
        }
        if (ptquan.isEmpty()) {
            ptQuan.error = "Quantity"
        }
        if (ptsupname.isEmpty()) {
            ptSupName.error = "Supplier Name"
        }
        if (ptpri.isEmpty()) {
            ptPri.error = "Price"
        }

        val proid = dbRef.push().key!!

        val product = ProductModel(proid, ptproname, ptquan, ptsupname, ptpri)

        dbRef.child(proid).setValue(product)
            .addOnCompleteListener {
                Toast.makeText(this, "Data Inserted Successfully", Toast.LENGTH_LONG).show()

                ptProName.text.clear()
                ptQuan.text.clear()
                ptSupName.text.clear()
                ptPri.text.clear()

            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }


    }
}

