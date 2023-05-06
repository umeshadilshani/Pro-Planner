//IT21321504
//Gunawardana N.B.C.A.W.

package com.example.proplanner.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proplanner.R
import com.example.proplanner.activities.EditSales
import com.example.proplanner.activities.Sales
import com.example.proplanner.models.SalesModel


class SalesAdapter(dataList1: Sales, private val dataList: List<SalesModel>):RecyclerView.Adapter<MyViewHolder>() {


    // This method inflates the layout for a single item in the list
    // creates a new MyViewHolder
    // object to hold references to the views within that layout.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPlan= dataList[position]
        holder.recPName.text =  currentPlan.prName // Set the product name
        holder.recPDes.text = currentPlan.prBatchNo // Set the product batch number

        holder.recCard.setOnClickListener{
            // Create a new Intent that launches the EditSales activity
            // Pass the details of the selected SalesModel object to the EditSales activity as extras in the Intent
            val intent = Intent(holder.itemView.context, EditSales::class.java)

            intent.putExtra("productName", currentPlan.prName)
            intent.putExtra("productBatchNo", currentPlan.prBatchNo)
            intent.putExtra("productQuantity", currentPlan.prQuan)
            intent.putExtra("productUnitPrice", currentPlan.prUniPri)
            intent.putExtra("productDate", currentPlan.prDate)
            // Start the EditSales activity
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}

//class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//    var recCard: CardView
//    var recPName: TextView
//    var recPDes: TextView
//
//    init{
//        recCard = itemView.findViewById(R.id.recCard)
//        recPName = itemView.findViewById(R.id.AprName)
//        recPDes = itemView.findViewById(R.id.AprBatchNo)
//    }
//}