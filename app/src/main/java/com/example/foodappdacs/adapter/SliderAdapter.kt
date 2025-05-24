package com.example.foodappdacs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodappdacs.api.UrlImage
import com.example.foodappdacs.databinding.ItemSliderBinding
import com.example.foodappdacs.model.Slider
import com.squareup.picasso.Picasso

class SliderAdapter(private val sliders: List<Slider>) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>(){
    inner class SliderViewHolder(private val binding: ItemSliderBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(slider: Slider){
            val urlImage = UrlImage.urlImage
            Picasso.get().load(urlImage + slider.sliderImage).into(binding.imageId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSliderBinding.inflate(inflater, parent, false)
        return SliderViewHolder(binding)
    }

    override fun getItemCount()= sliders.size

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(sliders[position])
    }

}