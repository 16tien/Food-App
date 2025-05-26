package com.example.foodappdacs.api

import com.example.foodappdacs.model.LoginRequest
import com.example.foodappdacs.model.LoginResponse
import com.example.foodappdacs.model.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("login") // đổi path theo API của bạn
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("register")
    fun register(@Body request: RegisterRequest): Call<ApiResponse>
}