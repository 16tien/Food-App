package com.example.foodappdacs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodappdacs.api.UrlImage
import com.example.foodappdacs.databinding.ItemDetailOrderBinding
import com.example.foodappdacs.model.Food
import com.squareup.picasso.Picasso

class OrderDetailAdapter(private var foods: List<Food>, private var qtys: List<Int>):
    RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder>() {

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class OrderDetailViewHolder(private var binding: ItemDetailOrderBinding):RecyclerView.ViewHolder(binding.root){
        var tvName =binding.tvName
        var tvPrice = binding.tvPrice
        var tvQty = binding.tvQty
        var image = binding.imageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailAdapter.OrderDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDetailOrderBinding.inflate(inflater, parent, false)
        return OrderDetailViewHolder(binding)
    }
    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        var order = foods[position]
        var qty = qtys[position]
        holder.tvName.text = order.foodName
        holder.tvQty.text = "Số lượng: ${qty.toString()}"
        holder.tvPrice.text = order.getPrice()
        Picasso.get().load(UrlImage.urlImage + order.foodImage).into(holder.image)

    }

    override fun getItemCount()= foods.size
}




