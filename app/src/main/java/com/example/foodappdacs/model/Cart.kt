package com.example.foodappdacs.model

import com.google.gson.annotations.SerializedName

data class Cart(
    @SerializedName("cart_id") val cartId: Int?=null,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("food_id") val foodId:Int,
    @SerializedName("product_qty") val productQty: Int,
)