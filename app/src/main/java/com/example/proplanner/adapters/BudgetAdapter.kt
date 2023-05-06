//IT21318320 - Silva T.U.D
package com.example.proplanner.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proplanner.R
import com.example.proplanner.models.Budget

class BudgetAdapter (private val budgetList: ArrayList<Budget>) : RecyclerView.Adapter<BudgetAdapter.ViewHolder>(){
    private lateinit var mListener : OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType : Int) : ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.budget_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder : ViewHolder, position: Int) {
        //Accessing data
        val currentBudget = budgetList[position]
        holder.tvPrjName.text = currentBudget.projectName
        holder.tvPrjBudget.text = (currentBudget.totalBudget).toString()
        holder.tvPrjAvailable.text = (currentBudget.available).toString()
    }

    override fun getItemCount() : Int{
        return budgetList.size
    }

    class ViewHolder(itemView: View, clickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView){
        //Setting data
        val tvPrjName : TextView = itemView.findViewById(R.id.tvSetProjectName)
        val tvPrjBudget : TextView = itemView.findViewById(R.id.tvSetBudget)
        val tvPrjAvailable : TextView = itemView.findViewById(R.id.tvSetAvailable)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}