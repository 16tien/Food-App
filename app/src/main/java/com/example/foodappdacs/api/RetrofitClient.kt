package com.example.foodappdacs.api

import com.example.app.utils.PrefManager
import com.example.foodappdacs.auth.AuthInterceptor
import com.example.foodappdacs.auth.TokenAuthenticator
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "http://192.168.1.4/AppDACS/api/"
    private val gson = GsonBuilder().setLenient().create()
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor())
        .authenticator(TokenAuthenticator())
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    fun createApiCategory(): CategoryApi {
        return retrofit.create(CategoryApi::class.java)
    }
    fun createApiFood():FoodApi{
        return retrofit.create(FoodApi::class.java)
    }
    fun createApiCart():CartApi{
        return retrofit.create(CartApi::class.java)
    }
    fun createApiAddress():AddressApi{
        return retrofit.create(AddressApi::class.java)
    }
    fun createApiOrder(): OrderApi{
        return retrofit.create(OrderApi::class.java)
    }
    fun createApiUser(): UserApi{
        return retrofit.create(UserApi::class.java)
    }
}