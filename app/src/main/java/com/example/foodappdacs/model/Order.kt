package com.example.foodappdacs.model

import com.google.gson.annotations.SerializedName

class Order(
    val order_id: Int?,
    val address_id: Int,
    val order_status: Int,
    val total_price: Int,
    @SerializedName("created_at")
    val created_at: String
) {
}