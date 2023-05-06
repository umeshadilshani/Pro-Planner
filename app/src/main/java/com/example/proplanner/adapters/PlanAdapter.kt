package com.example.proplanner.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proplanner.R
import com.example.proplanner.activities.DetailedPlan
import com.example.proplanner.activities.PlanDashboard
import com.example.proplanner.models.DataClass

class PlanAdapter (dataList1: PlanDashboard, private val dataList: List<DataClass>): RecyclerView.Adapter<MyViewHolder>() {

    // Create a new view holder and inflate the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }

    // Replace the contents of a view holder
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPlan= dataList[position]
        holder.recPName.text =  currentPlan.dataProName
        holder.recPDes.text = currentPlan.dataProDesc

        // Set an on click listener to the card view
        holder.recCard.setOnClickListener{

            val intent = Intent(holder.itemView.context, DetailedPlan::class.java)

            // Add plan details as extras to the intent
            intent.putExtra("planName", currentPlan.dataProName)
            intent.putExtra("planDesc", currentPlan.dataProDesc)
            intent.putExtra("planPeriod", currentPlan.dataTPeriod)
            intent.putExtra("planTasks", currentPlan.dataTask)
            // Start the DetailedPlan activity
            holder.itemView.context.startActivity(intent)
        }

    }

    // Return the number of items in the data list
    override fun getItemCount(): Int {
        return dataList.size
    }

}

// Define the view holder class that extends RecyclerView.ViewHolder
class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var recPName: TextView
    var recPDes: TextView
    var recCard: CardView

    init{
        recCard = itemView.findViewById(R.id.recCard)
        recPName = itemView.findViewById(R.id.recPName)
        recPDes = itemView.findViewById(R.id.recPDes)
    }
}