package com.example.foodappdacs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodappdacs.api.UrlImage
import com.example.foodappdacs.databinding.ItemOrderCartBinding
import com.example.foodappdacs.model.Food
import com.squareup.picasso.Picasso

class PayAdapter(private var foods: List<Food>, private val qty: List<Int>):
    RecyclerView.Adapter<PayAdapter.CartViewHolder>() {

    inner class CartViewHolder(private var binding: ItemOrderCartBinding):RecyclerView.ViewHolder(binding.root){
        val urlImage = UrlImage.urlImage
        val name = binding.tvName
        val img = binding.imgFood
        val qty = binding.qty
        val price = binding.tvPrice


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayAdapter.CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderCartBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding)
    }
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val food =foods[position]
        var qty = qty[position]

        holder.name.text = food.foodName
        holder.price.text = food.getPrice()
        holder.qty.text = qty.toString()
        Picasso.get().load(holder.urlImage + food.foodImage).into(holder.img)
    }

    override fun getItemCount()= foods.size

    public fun getTotal():Int{
        var total = 0
        for ((index,food) in foods.withIndex()){
            total+= food.foodPrice * qty[index]
        }
        return total
    }
    public fun getQty():Int{
        var total = 0
        for((index,qt) in qty.withIndex()){
            total+= qt
        }
        return total
    }
}




