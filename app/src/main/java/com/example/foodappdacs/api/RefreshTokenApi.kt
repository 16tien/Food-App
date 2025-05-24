package com.example.foodappdacs.api

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST

interface RefreshTokenApi {
    @POST("refresh")
    fun refreshAccessToken(
        @Header("Authorization") refreshToken: String
    ): Call<RefreshResponse>
}

data class RefreshResponse(
    val accessToken: String,
    val expiresIn: Int
)
