package com.example.foodappdacs.api

import com.example.foodappdacs.model.Slider
import retrofit2.http.GET

interface SliderApi  {
    @GET("sliders")
    suspend fun getSliders(): List<Slider>
}
