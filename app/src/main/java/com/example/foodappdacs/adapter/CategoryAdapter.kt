package com.example.foodappdacs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodappdacs.api.UrlImage
import com.example.foodappdacs.databinding.ItemCategoryBinding
import com.example.foodappdacs.model.Category
import com.squareup.picasso.Picasso

class CategoryAdapter(private var categories: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    inner class CategoryViewHolder(private val binding: ItemCategoryBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(category: Category){
            binding.item = category
            binding.executePendingBindings()
            val urlImage = UrlImage.urlImage
            Picasso.get().load(urlImage + category.categoryImage).into(binding.imageId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount()= categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }
    fun setCategories(categories: List<Category>) {
        this.categories = categories
        notifyDataSetChanged()
    }

}