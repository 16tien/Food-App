package com.example.foodappdacs.api

import com.example.foodappdacs.model.Food
import com.example.foodappdacs.model.Order
import com.example.foodappdacs.model.OrderDetail
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

interface OrderApi {
    @FormUrlEncoded
    @POST("crtOrder")
    fun createOrder(
        @Field("address_id") addressId: Int,
        @Field("total") total: Int,
        @Field("food_id") foodId: Int,
        @Field("qty") qty: Int,
        @Field("user_id") userId: Int
    ) : Call<ApiResponse>

    @GET("orders/{user_id}")
    suspend fun getOrders(@Path("user_id")id:Int) : List<Order>

    @GET("getOrderDetails/{order_id}")
    suspend fun getOrderDetails(@Path("order_id") orderId: Int) : List<Food>

    @GET("getQtyDetails/{order_id}")
    suspend fun getQty(@Path("order_id") orderId: Int) : List<Int>

    @GET("orderCart/{user_id}")
    suspend fun createOrderCart(@Path("user_id") user_id: Int) : Call<ApiResponse>

    @FormUrlEncoded
    @POST("orderCart")
    fun createOrderCart(
        @Field("user_id") user_id: Int,
        @Field("total") total: Int,
    ) : Call<ApiResponse>
}