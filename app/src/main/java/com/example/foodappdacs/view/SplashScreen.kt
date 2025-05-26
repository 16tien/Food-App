package com.example.foodappdacs.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.app.utils.PrefManager
import com.example.foodappdacs.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {

    companion object {
        private const val SPLASH_TIME = 2000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        PrefManager.init(applicationContext)
//        PrefManager.clearAll()
        lifecycleScope.launch {
            delay(SPLASH_TIME)
            val isLoggedIn = PrefManager.getLoginStatus()

            val nextActivity = if (isLoggedIn) {
                MainActivity::class.java
            } else {
                LoginActivity::class.java
            }
            startActivity(Intent(this@SplashScreen, nextActivity))
            finish()
        }
    }
}
