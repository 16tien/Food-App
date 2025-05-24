package com.example.foodappdacs.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodappdacs.adapter.OrderDetailAdapter
import com.example.foodappdacs.api.OrderApi
import com.example.foodappdacs.api.RetrofitClient
import com.example.foodappdacs.databinding.ActivityFoodDetailBinding
import com.example.foodappdacs.databinding.ActivityOrderDetailBinding
import com.example.foodappdacs.model.Food
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailBinding
    private lateinit var orderApi: OrderApi
    private lateinit var adapter: OrderDetailAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setDisplayShowTitleEnabled(true)
            it.setDisplayShowHomeEnabled(false)
            it.setDisplayHomeAsUpEnabled(true)
        }
        val bundle = intent.extras
        var orderId = bundle?.getInt("orderId")
        var a =1+1
        loadOrderDetail(orderId)
    }

    private fun loadOrderDetail(orderId: Int?) {
        orderApi = RetrofitClient.createApiOrder()
        CoroutineScope(Dispatchers.IO).launch {
            var qtys:List<Int> = orderApi.getQty(orderId!!)
            var orderDetails:List<Food> = orderApi.getOrderDetails(orderId!!)
            var total = 0;

            adapter = OrderDetailAdapter(orderDetails, qtys)
            runOnUiThread {
                binding.recyclerView.adapter = adapter
            }

        }
    }

}