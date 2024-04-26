package com.midtermexam.DeLeonTracker
// Import necessary libraries
import android.content.Context // Import R class for accessing resources
import android.view.LayoutInflater // Import LayoutInflater for inflating views
import android.view.View // Import View class for UI elements
import android.view.ViewGroup // Import ViewGroup for view hierarchy
import android.widget.TextView // Import TextView for displaying text
import androidx.recyclerview.widget.RecyclerView // Import RecyclerView for implementing adapter

// Define the list_adapter class inheriting from RecyclerView.Adapter
class list_adapter (val requiredContext: Context,var Details:ArrayList<listdata>) :
    RecyclerView.Adapter<list_adapter.viewholde>() {

    // Define the view holder class as an inner class of list_adapter
   inner class viewholde(itemView: View):RecyclerView.ViewHolder(itemView) {
        // Initialize TextViews for item_name, item_price, and item_date
        val item_name:TextView=itemView.findViewById(R.id.textView2)
        val item_price:TextView=itemView.findViewById(R.id.textView3)
        val item_date:TextView=itemView.findViewById(R.id.textView9)

    }

    // Function to update the list with a new list
    fun updateList(newList: ArrayList<listdata>) {
        Details = newList
        notifyDataSetChanged()
    }

    // Override onCreateViewHolder method
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholde {
        // Inflate the item layout using LayoutInflater
        val view=LayoutInflater.from(parent.context).inflate(R.layout.items,parent,false)
        return viewholde(view) // Return a new instance of viewholde with the inflated view
    }

    // Override getItemCount method
    override fun getItemCount(): Int {

        return Details.size
    }

    override fun onBindViewHolder(holder: viewholde, position: Int) {

        holder.item_name.text=Details[position].item_name
        holder.item_price.text=Details[position].item_price
        holder.item_date.text=Details[position].item_date
    }

}
