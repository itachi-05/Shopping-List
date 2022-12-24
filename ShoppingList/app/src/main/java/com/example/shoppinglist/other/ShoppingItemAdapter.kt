package com.example.shoppinglist.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.data.db.entities.ShoppingItem
import com.example.shoppinglist.ui.shoppingList.ShoppingViewModel

class ShoppingItemAdapter(
    var items: List<ShoppingItem>,
    private val viewModel: ShoppingViewModel
): RecyclerView.Adapter<ShoppingItemAdapter.ShoppingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_item, parent, false)
        return ShoppingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val currentShoppingItem = items[position]
        holder.tvName.text = currentShoppingItem.name
        holder.tvAmount.text = currentShoppingItem.amount.toString()
        holder.ivDelete.setOnClickListener{
            viewModel.delete(currentShoppingItem)
        }
        holder.ivPlus.setOnClickListener{
            currentShoppingItem.amount++
            viewModel.upsert(currentShoppingItem)
        }
        holder.ivMinus.setOnClickListener{
            if(currentShoppingItem.amount>0){
                currentShoppingItem.amount--
                viewModel.upsert(currentShoppingItem)
            }
            if(currentShoppingItem.amount==1){
                viewModel.delete(currentShoppingItem)
            }
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    class ShoppingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        val ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)
        val ivPlus: ImageView = itemView.findViewById(R.id.ivPlus)
        val ivMinus: ImageView = itemView.findViewById(R.id.ivMinus)
    }
}