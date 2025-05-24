package com.example.foodappdacs.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.example.foodappdacs.api.*
import com.example.foodappdacs.databinding.ActivityPayBinding
import com.example.foodappdacs.model.Address
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class PayActivity : AppCompatActivity() {
    private lateinit var payBinding: ActivityPayBinding
    private lateinit var foodApi: FoodApi
    private lateinit var addressApi: AddressApi
    private lateinit var orderApi: OrderApi
    @SuppressLint("SetTextIn")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        payBinding = ActivityPayBinding.inflate(layoutInflater)
        setContentView(payBinding.root)

        setSupportActionBar(payBinding.toolbar)
        supportActionBar?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setDisplayShowTitleEnabled(true)
            it.setDisplayShowHomeEnabled(false)
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "THANH TOÁN"
        }

        val bundle = intent.extras
        var foodId = bundle?.getInt("foodId")
        var qty = bundle?.getInt("qty")
        val sharedPreferences = getSharedPreferences("TIEN", MODE_PRIVATE)
        val userId = sharedPreferences.getInt("userId", 0)

        var ship:Int = 10000
        foodApi= RetrofitClient.createApiFood()
        CoroutineScope(Dispatchers.IO).launch {
            val food  = foodApi.getFoodById(foodId!!)
            var total = food.foodPrice* qty!! +ship
            runOnUiThread {
                payBinding.qty.text = "Số lượng: ${qty.toString()}"
                payBinding.tvName.text = food.foodName
                payBinding.tvPrice.text = "Giá: ${food.getPrice()}"
                val urlImage = UrlImage.urlImage
                Picasso.get().load(urlImage + food.foodImage).into(payBinding.imgFood)
                payBinding.tvPriceTotal.text = "Giá tiền: ${getPrice(food.foodPrice* qty)}"
                payBinding.tvShip.text = "Phí Ship: ${getPrice(ship)}"
                payBinding.tvTotal.text = "Tổng số tiền: ${getPrice(total)}"
            }
        }
        addressApi = RetrofitClient.createApiAddress()
        CoroutineScope(Dispatchers.IO).launch {
            val address: Address = addressApi.getAddressById(userId)
            runOnUiThread {
                payBinding.tvAddress.text=address.getAddress()
                payBinding.tvPhone.text = address.phone_address.toString()
            }
        }
        payBinding.btnOrder.setOnClickListener {
            orderApi = RetrofitClient.createApiOrder()
            CoroutineScope(Dispatchers.IO).launch {
                val address: Address = addressApi.getAddressById(userId)
                val food  = foodApi.getFoodById(foodId!!)
                var total = food.foodPrice* qty!! +ship
                var addressId = address.address_id
                orderApi.createOrder(addressId,total,foodId,qty, userId).enqueue(object : Callback<ApiResponse?> {
                    override fun onResponse(call: Call<ApiResponse?>, response: Response<ApiResponse?>) {
                        if (response.isSuccessful) {
                            val message = response.body()?.message ?: "Empty response"
                            Toast.makeText(this@PayActivity,message,Toast.LENGTH_SHORT).show()

                        } else {
                            val errorResponse = response.errorBody()?.string() ?: "Unknown error"
                            Toast.makeText(this@PayActivity,errorResponse,Toast.LENGTH_SHORT).show()

                        }
                    }

                    override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                        Toast.makeText(applicationContext, "API call failed", Toast.LENGTH_SHORT).show()
                        // Log the error for debugging purposes
                        Log.e("FoodDetail", "API call failed", t)
                    }
                })
            }
        }




    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    private fun getPrice(number: Int): String {
        val formatter = NumberFormat.getInstance(Locale("vi", "VN")) as DecimalFormat
        formatter.applyPattern("#,###")
        return formatter.format(number) + " đ"
    }
}