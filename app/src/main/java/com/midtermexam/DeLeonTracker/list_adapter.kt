package com.midtermexam.DeLeonTracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class list_adapter (val requiredContext: Context,var Details:ArrayList<listdata>) :
    RecyclerView.Adapter<list_adapter.viewholde>() {

   inner class viewholde(itemView: View):RecyclerView.ViewHolder(itemView) {

        val item_name:TextView=itemView.findViewById(R.id.textView2)
        val item_price:TextView=itemView.findViewById(R.id.textView3)
        val item_date:TextView=itemView.findViewById(R.id.textView9)

    }

    fun updateList(newList: ArrayList<listdata>) {
        Details = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholde {

        val view=LayoutInflater.from(parent.context).inflate(R.layout.items,parent,false)
        return viewholde(view)
    }

    override fun getItemCount(): Int {

        return Details.size
    }

    override fun onBindViewHolder(holder: viewholde, position: Int) {

        holder.item_name.text=Details[position].item_name
        holder.item_price.text=Details[position].item_price
        holder.item_date.text=Details[position].item_date
    }

}
