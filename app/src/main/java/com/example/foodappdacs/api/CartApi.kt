package com.example.foodappdacs.api

import com.example.foodappdacs.model.Cart
import com.example.foodappdacs.model.Food
import retrofit2.http.*

interface CartApi {

    // Lấy danh sách Cart theo userId
    @GET("cart/user/{userId}")
    suspend fun getCarts(@Path("userId") userId: Int): List<Cart>

    // Lấy danh sách số lượng (tuỳ backend của bạn)
    @GET("cart/user/{userId}/qty")
    suspend fun getQty(@Path("userId") userId: Int): List<Int>

    // Lấy danh sách món ăn trong giỏ (nếu backend cung cấp API này)
    @GET("cart/user/{userId}/foods")
    suspend fun getCartFoods(@Path("userId") userId: Int): List<Food>

    // Thêm món ăn vào giỏ
    @POST("cart")
    suspend fun addToCart(@Body cart: Cart):Cart

    // Cập nhật số lượng trong cart
    @PUT("cart/{cartId}")
    suspend fun updateCart(@Path("cartId") cartId: Int, @Body updatedCart: Cart): Cart

    // Xoá item khỏi cart
    @DELETE("cart/{cartId}")
    suspend fun deleteCart(@Path("cartId") cartId: Int): Void

    @GET("cart/user/{userId}/foods")
    suspend fun getCart(@Path("userId") userId: Int): List<Food>
}
