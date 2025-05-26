package com.example.foodappdacs.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.utils.PrefManager
import com.example.foodappdacs.R
import com.example.foodappdacs.api.RetrofitClient
import com.example.foodappdacs.model.LoginRequest
import com.example.foodappdacs.model.LoginResponse
import retrofit2.Call
import retrofit2.Response

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etEmail);
        val etPassword = findViewById<EditText>(R.id.etPassword);
        val btnLogin = findViewById<Button>(R.id.btnLogin);
        val tvRegister = findViewById<TextView>(R.id.tvRegister);

        btnLogin.setOnClickListener{
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(
                    this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return@setOnClickListener;
            }

            val request = LoginRequest(email, password)
            val call = RetrofitClient.createApiUser().login(request);

            call.enqueue(object : retrofit2.Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>,
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null && body.status=="success") {
                            val token = body.accessToken
                            val userId= body.userId
                            val refreshToken = body.refreshToken
                            PrefManager.setLoginStatus(true)
                            PrefManager.saveToken(token)
                            PrefManager.setUserId(userId)
                            PrefManager.saveRefreshToken(refreshToken)
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, body?.message ?: "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Không kết nối được: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
        tvRegister.setOnClickListener {
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}