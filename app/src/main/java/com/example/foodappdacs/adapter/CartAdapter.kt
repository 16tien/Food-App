package com.example.foodappdacs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodappdacs.api.UrlImage
import com.example.foodappdacs.databinding.ItemCartBinding
import com.example.foodappdacs.model.Cart
import com.example.foodappdacs.model.Food
import com.squareup.picasso.Picasso

class CartAdapter(
    private var foods: List<Food>,
    private var qty: List<Int>,
    private var cart: List<Cart>,
    private val listener: OnQuantityChangeListener
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food, quantity: Int, position: Int) {
            binding.tvName.text = food.foodName
            binding.tvPrice.text = food.getPrice()
            binding.tvNumber.text = quantity.toString()
            Picasso.get().load(UrlImage.urlImage + food.foodImage).into(binding.imgFood)

            binding.btnIncrease.setOnClickListener {
                val newQty = quantity + 1
                listener.onQuantityChanged(position, newQty)
            }

            binding.btnReduce.setOnClickListener {
                if (quantity > 0) {
                    val newQty = quantity - 1
                    listener.onQuantityChanged(position, newQty)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(foods[position], qty[position], position)
    }

    override fun getItemCount() = foods.size

    fun updateData(newFoods: List<Food>, newQty: List<Int>, newCart: List<Cart>) {
        foods = newFoods
        qty = newQty
        cart = newCart
        notifyDataSetChanged()
    }
}





