package com.example.saltandwhet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecipeAdapter(private val itemList: ArrayList<Recipe>) : RecyclerView.Adapter<RecipeAdapter.myViewHolder>() {


    class myViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val itemImageView: ImageView = itemView.findViewById(R.id.ivRecipeImg)
        val itemNameView: TextView = itemView.findViewById(R.id.tvRecipeTitle)
        val itemDescView: TextView = itemView.findViewById(R.id.tvRecipeDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_card, parent, false)
        return myViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentItem = itemList[position]
        Glide.with(holder.itemView.context)
            .load(currentItem.itemImage)
            .into(holder.itemImageView)
        holder.itemNameView.text = currentItem.itemName
        holder.itemDescView.text = currentItem.itemArea
    }
}