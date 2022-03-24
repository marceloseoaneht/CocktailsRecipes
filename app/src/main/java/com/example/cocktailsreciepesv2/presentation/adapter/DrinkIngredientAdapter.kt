package com.example.cocktailsreciepesv2.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailsreciepesv2.databinding.ItemIngredientListBinding
import com.example.cocktailsreciepesv2.domain.model.Ingredient

class DrinkIngredientAdapter(val items: List<Ingredient>) :
    RecyclerView.Adapter<DrinkIngredientAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemIngredientListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: Ingredient) {
            binding.tvIngredient.text = ingredient.name
            binding.tvMeasure.text = ingredient.measure
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return ViewHolder(ItemIngredientListBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}