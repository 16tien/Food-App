package com.example.foodappdacs.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.example.app.utils.PrefManager
import com.example.foodappdacs.R
import com.example.foodappdacs.adapter.CartAdapter
import com.example.foodappdacs.adapter.PayAdapter
import com.example.foodappdacs.api.AddressApi
import com.example.foodappdacs.api.ApiResponse
import com.example.foodappdacs.api.OrderApi
import com.example.foodappdacs.api.RetrofitClient
import com.example.foodappdacs.databinding.ActivityPayCartBinding
import com.example.foodappdacs.model.Address
import com.example.foodappdacs.model.Cart
import com.example.foodappdacs.model.Food
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class PayCartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayCartBinding
    private lateinit var addressApi: AddressApi
    private lateinit var adapter : PayAdapter
    private lateinit var orderApi: OrderApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userId = PrefManager.getUserId()

        supportActionBar?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayShowHomeEnabled(false)
            it.setDisplayHomeAsUpEnabled(true)
        }

        addressApi = RetrofitClient.createApiAddress()
        CoroutineScope(Dispatchers.IO).launch {
            val address: Address = addressApi.getAddressById(userId)
            runOnUiThread {
                binding.tvAddress.text=address.getAddress()
                binding.tvPhone.text = address.phone_address.toString()
            }
        }
        binding.btnOrder.setOnClickListener {
                orderApi = RetrofitClient.createApiOrder()
                val cartApi=RetrofitClient.createApiCart()
                CoroutineScope(Dispatchers.IO).launch {
                    val qty:List<Int> = cartApi.getQty(userId)
                    val foodCart:List<Food> = cartApi.getCart(userId)
                    adapter = PayAdapter(foodCart,qty)
                    var total =adapter.getTotal()+10000
                    orderApi.createOrderCart(userId,total).enqueue(object :
                        Callback<ApiResponse?> {
                        override fun onResponse(call: Call<ApiResponse?>, response: Response<ApiResponse?>) {
                            if (response.isSuccessful) {
                                val message = response.body()?.message ?: "Empty response"
                                Toast.makeText(this@PayCartActivity,message, Toast.LENGTH_SHORT).show()
                                finish()

                            } else {
                                val errorResponse = response.errorBody()?.string() ?: "Unknown error"
                                Toast.makeText(this@PayCartActivity,errorResponse, Toast.LENGTH_SHORT).show()

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

        loadCart(userId)

    }

    private fun loadCart(userId: Int) {
        val cartApi=RetrofitClient.createApiCart()
        CoroutineScope(Dispatchers.IO).launch {
            val qty:List<Int> = cartApi.getQty(userId)
            val foodCart:List<Food> = cartApi.getCart(userId)
            runOnUiThread {
                adapter = PayAdapter(foodCart,qty)
                var total = adapter.getTotal()+10000
                binding.recyclerView.adapter = adapter
                binding.tvTotal.text =  "Tổng cổng: ${getPrice(total) }"
                binding.tvShip.text = "Phí ship: 10.000đ"
                binding.tvPriceTotal.text ="Giá tiền: ${getPrice(adapter.getTotal())}"
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    fun getPrice(price: Int): String {
        val formatter = NumberFormat.getInstance(Locale("vi", "VN")) as DecimalFormat
        formatter.applyPattern("#,###")
        return formatter.format(price) + " đ"
    }
}