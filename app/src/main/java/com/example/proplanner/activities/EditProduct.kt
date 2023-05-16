//it21276996
//Fernando.W.Y.M
package com.example.proplanner.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import com.example.proplanner.models.ProductModel
import com.example.proplanner.R
import android.content.Intent
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import androidx.appcompat.app.AlertDialog
import com.example.proplanner.activities.InventoryDashboard
import com.example.proplanner.activities.ProductFetch

class EditProduct : AppCompatActivity() {

    private lateinit var editTextTextPersonName2: TextView
    private lateinit var editTextTextPersonName3: TextView
    private lateinit var editTextTextPersonName5: TextView
    private lateinit var editTextTextPersonName8: TextView
    private lateinit var button: Button
    private lateinit var button2: Button
//    private lateinit var imageButton7: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_product)

        // Initialize view elements and populate with data
        initView()
        setValuesToViews()


        button.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("proid").toString(),
                intent.getStringExtra("ptproname").toString()

            )
        }
        button2.setOnClickListener{
            deleteRecord(intent.getStringExtra("proid").toString())
        }
//
        val imageButton: ImageButton = findViewById(R.id.imageButton7)

        // Set click listener for the imageButton
        imageButton.setOnClickListener {
            // Start the InventoryDashboard activity
            val intent = Intent(this, InventoryDashboard::class.java)
            startActivity(intent)
        }

    }

    // Delete the record with the specified id from Firebase Realtime Database
    private fun deleteRecord(id: String){
        val dbRef = FirebaseDatabase.getInstance().getReference("Product").child(id)

        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Product Data Deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this, ProductFetch::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Error when deleting : ${error.message}", Toast.LENGTH_LONG).show()
        }
    }


    // Initialize view elements by finding them in the layout file
    private fun initView() {

//        editTextTextPersonName2 = findViewById(R.id.editTextTextPersonName2)
        editTextTextPersonName3 = findViewById(R.id.editTextTextPersonName3)
        editTextTextPersonName8 = findViewById(R.id.editTextTextPersonName8)
        editTextTextPersonName5= findViewById(R.id.editTextTextPersonName5)


        button = findViewById(R.id.button)
        button2 = findViewById(R.id.button2)
    }

    private fun setValuesToViews() {


//       editTextTextPersonName2.text = intent.getStringExtra("proid")
        editTextTextPersonName3.text = intent.getStringExtra("ptproname")
        editTextTextPersonName8.text = intent.getStringExtra("ptquan")
        editTextTextPersonName5.text = intent.getStringExtra("ptsupname")


    }
    // Open a dialog to update the selected product's data
    private fun openUpdateDialog(
        proid: String ,ptproname: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_product, null)

        mDialog.setView(mDialogView)

        // Find view elements in the dialog layout file
        val ptProName = mDialogView.findViewById<EditText>(R.id.ptProName)
        val ptQuan = mDialogView.findViewById<EditText>(R.id.ptQuan)
        val ptSupName = mDialogView.findViewById<EditText>(R.id.ptSupName)

        val button3 = mDialogView.findViewById<Button>(R.id.button3)

        //
        ptProName.setText(intent.getStringExtra("ptproname") ?: "")
        ptQuan.setText(intent.getStringExtra("ptquan") ?: "")

        ptSupName.setText(intent.getStringExtra("ptsupname") ?: "")


        mDialog.setTitle("Updating $ptproname Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        button3.setOnClickListener {
            updateProData(
                proid,
                ptProName.text.toString(),
                ptQuan.text.toString(),
                ptSupName.text.toString()





            )

            Toast.makeText(applicationContext, "Product Data Updated", Toast.LENGTH_LONG).show()


            editTextTextPersonName3.text =
                Editable.Factory.getInstance().newEditable(  ptProName.text.toString())
            editTextTextPersonName8.text =
                Editable.Factory.getInstance().newEditable(ptQuan.text.toString())
            editTextTextPersonName5.text =
                Editable.Factory.getInstance().newEditable(ptSupName.text.toString())

            alertDialog.dismiss()
        }
    }



    private fun updateProData(id: String, productName: String, quantity: String, supplierName: String){
        val dbRef = FirebaseDatabase.getInstance().getReference("Product").child(id)
        val proInfo = ProductModel(id, productName, quantity, supplierName)
        dbRef.setValue(proInfo)
    }
}


