package com.example.foodappdacs.view

import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.app.utils.PrefManager
import com.example.foodappdacs.R
import com.example.foodappdacs.api.ApiResponse
import com.example.foodappdacs.api.CartApi
import com.example.foodappdacs.api.FoodApi
import com.example.foodappdacs.api.RetrofitClient
import com.example.foodappdacs.api.UrlImage
import com.example.foodappdacs.databinding.ActivityFoodDetailBinding
import com.example.foodappdacs.model.Cart
import com.example.foodappdacs.viewModel.FoodViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodDetail : AppCompatActivity() {
    private lateinit var binding: ActivityFoodDetailBinding
    private lateinit var cartApi : CartApi
    private lateinit var foodApi: FoodApi
    private lateinit var orderDialog: OrderDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)  // Hiện nút back
            setDisplayShowTitleEnabled(false)
        }
        val bundle = intent.extras
        val foodId = bundle?.getInt("foodId")
        val foodViewModel = ViewModelProvider(this)[FoodViewModel::class.java]
        if (foodId != null) {
            foodViewModel.getFoodById(foodId.toInt())
        }

        foodViewModel.food.observe(this, Observer {
            binding.food = it
            binding.lifecycleOwner = this

            val urlImage = UrlImage.urlImage
            Picasso.get().load(urlImage + it.foodImage).into(binding.imageFood)
        })

        binding.btnAddCart.setOnClickListener{addCart(foodId!!)}
        binding.btnBuy.setOnClickListener {
            orderDialog= OrderDialog(this, foodId!!)
            orderDialog.showDialog()
        }
    }
    // add Cart
    private fun addCart(foodId: Int) {
        val userId = PrefManager.getUserId()
        val cart = Cart(cartId = null, userId = userId, foodId = foodId, productQty = 1)
        cartApi = RetrofitClient.createApiCart()
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                Log.d("CartRequest", cart.toString())
                val response = cartApi.addToCart(cart)
                Log.d("CartResponse", response.toString())// Suspend function
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@FoodDetail, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@FoodDetail, "Lỗi khi thêm vào giỏ hàng", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}