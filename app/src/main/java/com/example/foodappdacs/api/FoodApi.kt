package com.example.foodappdacs.api

import androidx.lifecycle.LiveData
import com.example.foodappdacs.model.Food
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodApi {
    @GET("foods")
    suspend fun getFoods(): List<Food>

    @GET("food/{foodId}")
    suspend fun getFoodById(@Path("foodId")id:Int) : Food

    @GET("foods-by-ids")
    suspend fun getFoodsByIds(@Query("ids") ids: List<Int>): List<Food>
}