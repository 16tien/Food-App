package com.example.foodappdacs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodappdacs.api.UrlImage
import com.example.foodappdacs.databinding.ItemFoodBinding
import com.example.foodappdacs.model.Food
import com.squareup.picasso.Picasso

class FoodAdapter(private var foods: List<Food>):
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    inner class FoodViewHolder(private val binding: ItemFoodBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(food: Food){
            binding.item = food
            binding.executePendingBindings()
            val urlImage = UrlImage.urlImage
            Picasso.get().load(urlImage + food.foodImage).into(binding.imageId)
        }
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodAdapter.FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodBinding.inflate(inflater, parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodAdapter.FoodViewHolder, position: Int) {
        holder.bind(foods[position])
    }

    override fun getItemCount()= foods.size
    fun setFoods(foods: List<Food>) {
        this.foods = foods
        notifyDataSetChanged()
    }
}