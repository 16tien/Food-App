package com.example.app.utils

import android.content.Context
import android.content.SharedPreferences

class PrefManager {
    companion object {
        private const val PREF_NAME = "USER_SESSION"
        private lateinit var preferences: SharedPreferences

        fun init(context: Context) {
            preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }

        fun setLoginStatus(isLoggedIn: Boolean) {
            preferences.edit().putBoolean("logged", isLoggedIn).apply()
        }

        fun saveToken(token : String){
            preferences.edit().putString("token", token).apply()
        }

        fun getToken(): String? {
            return preferences.getString("token", null)
        }

        fun getLoginStatus(): Boolean {
            return preferences.getBoolean("logged", false)
        }

        fun setUserId(userId: Int) {
            preferences.edit().putInt("userId", userId).apply()
        }

        fun getUserId(): Int {
            return preferences.getInt("userId", -1)
        }
        fun saveRefreshToken(refreshToken : String){
            preferences.edit().putString("refreshToken", refreshToken).apply()
        }
        fun getRefreshToken(): String? {
            return preferences.getString("refreshToken", null)
        }
        fun clearAll() {
            preferences.edit().clear().apply()
        }
    }
}
