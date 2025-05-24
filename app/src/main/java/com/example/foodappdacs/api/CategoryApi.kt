package com.example.foodappdacs.api

import com.example.foodappdacs.model.Category
import retrofit2.http.GET

interface  CategoryApi {
    @GET("categories")
    suspend fun getCategories(): List<Category>
}