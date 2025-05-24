package com.example.foodappdacs.auth

import android.util.Log
import com.example.app.utils.PrefManager
import com.example.foodappdacs.api.RefreshTokenApi
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {

        if (responseCount(response) >= 2) return null

        val refreshToken = PrefManager.getRefreshToken()
        if (refreshToken.isNullOrEmpty()) return null

        val refreshApi = Retrofit.Builder()
            .baseUrl("http://192.168.1.4/AppDACS/api/") // Thay bằng URL thật
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RefreshTokenApi::class.java)

        val refreshResponse = refreshApi.refreshAccessToken("Bearer $refreshToken").execute()

        return if (refreshResponse.isSuccessful && refreshResponse.body() != null) {
            val newAccessToken = refreshResponse.body()!!.accessToken
            PrefManager.saveToken(newAccessToken)

            response.request.newBuilder()
                .header("Authorization", "Bearer $newAccessToken")
                .build()
        } else {
            null
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var priorResponse = response.priorResponse
        while (priorResponse != null) {
            count++
            priorResponse = priorResponse.priorResponse
        }
        return count
    }
}
