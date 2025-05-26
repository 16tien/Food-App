package com.example.foodappdacs.api

import com.example.foodappdacs.model.Cart
import com.example.foodappdacs.model.Food
import retrofit2.http.*

interface CartApi {

    @GET("cart/user/{userId}")
    suspend fun getCarts(@Path("userId") userId: Int): List<Cart>

    @GET("cart/user/{userId}/qty")
    suspend fun getQty(@Path("userId") userId: Int): List<Int>

    @POST("cart")
    suspend fun addToCart(@Body cart: Cart):Cart


    @GET("cart/user/{userId}/foods")
    suspend fun getCart(@Path("userId") userId: Int): List<Food>
}
