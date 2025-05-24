package com.example.foodappdacs.model

import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

data class Food(
    @SerializedName("food_id") val foodId: Int,
    @SerializedName("food_name") val foodName: String,
    @SerializedName("category_id") val categoryId: Int,
    @SerializedName("food_desc") val foodDesc: String,
    @SerializedName("food_price") val foodPrice: Int,
    @SerializedName("food_image") val foodImage: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
){

    fun getPrice(): String {
        val formatter = NumberFormat.getInstance(Locale("vi", "VN")) as DecimalFormat
        formatter.applyPattern("#,###")
        return formatter.format(foodPrice) + " đ"
    }
}

