package com.example.foodappdacs.api

import androidx.lifecycle.LiveData
import com.example.foodappdacs.model.Address
import com.example.foodappdacs.model.Food
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface AddressApi {
    @GET("address/{user_id}")
    suspend fun getAddressById(@Path("user_id") id: Int): Address
}