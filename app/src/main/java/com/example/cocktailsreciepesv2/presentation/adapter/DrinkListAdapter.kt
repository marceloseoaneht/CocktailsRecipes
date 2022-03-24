package com.example.cocktailsreciepesv2.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.cocktailsreciepesv2.R
import com.example.cocktailsreciepesv2.databinding.ItemDrinkListBinding
import com.example.cocktailsreciepesv2.domain.model.DrinkListElement

class DrinkListAdapter(val items: ArrayList<DrinkListElement>) :
    RecyclerView.Adapter<DrinkListAdapter.ViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    fun setList(items: List<DrinkListElement>) {
        this.items.clear()
        this.items.addAll(items)
    }

    class ViewHolder(binding: ItemDrinkListBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.tvCocktailTitle
        val image = binding.ivCocktailImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemDrinkListBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: DrinkListElement = items[position]
        holder.name.text = model.name
        holder.image.load(model.image) {
            placeholder(R.drawable.list_cocktail_image_placeholder)
            error(R.drawable.list_cocktail_image_placeholder)
            transformations(RoundedCornersTransformation(4F))
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(model.id)
            }
        }
    }
}