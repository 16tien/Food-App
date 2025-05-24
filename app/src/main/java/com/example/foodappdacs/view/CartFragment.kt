package com.example.foodappdacs.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.utils.PrefManager
import com.example.foodappdacs.adapter.CartAdapter
import com.example.foodappdacs.adapter.OnItemClickListener
import com.example.foodappdacs.adapter.OnQuantityChangeListener
import com.example.foodappdacs.adapter.OrderAdapter
import com.example.foodappdacs.api.CartApi
import com.example.foodappdacs.api.RetrofitClient
import com.example.foodappdacs.databinding.FragmentCartBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartApi: CartApi
    private lateinit var adapter: CartAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun loadCart(userId: Int) {
        cartApi = RetrofitClient.createApiCart()
        CoroutineScope(Dispatchers.IO).launch {
            val qty = cartApi.getQty(userId).toMutableList() // dùng MutableList để cập nhật được
            val foodCart = cartApi.getCart(userId)
            val carts = cartApi.getCarts(userId)

            requireActivity().runOnUiThread {
                adapter = CartAdapter(foodCart, qty, carts, object : OnQuantityChangeListener {
                    override fun onQuantityChanged(position: Int, newQuantity: Int) {
                        qty[position] = newQuantity
                        adapter.notifyItemChanged(position)

                        // Gửi cập nhật lên server nếu cần
                        CoroutineScope(Dispatchers.IO).launch {
//                            cartApi.updateCartQty(carts[position].cartId, newQuantity)
                        }

                        // Tính lại tổng tiền
                        val totalPrice = foodCart.indices.sumOf { i ->
                            foodCart[i].foodPrice * qty[i]
                        }

                        requireActivity().runOnUiThread {
                            binding.tvTotal.text = "Tổng tiền: ${getPrice(totalPrice)}"
                            binding.tvQty.text = "Số lượng: ${qty.sum()}"
                        }
                    }
                })
                binding.recyclerView.adapter = adapter

                // Hiển thị tổng tiền ban đầu
                val totalPrice = foodCart.indices.sumOf { i ->
                    foodCart[i].foodPrice * qty[i]
                }
                binding.tvQty.text = "Số lượng: ${qty.sum()}"
                binding.tvTotal.text = "Tổng tiền: ${getPrice(totalPrice)}"
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val userId = PrefManager.getUserId()
        loadCart(userId)
        binding.btnPay.setOnClickListener {
            val intent = Intent(requireContext(), PayCartActivity::class.java)
            startActivity(intent)
        }
    }
    fun getPrice(price: Int): String {
        val formatter = NumberFormat.getInstance(Locale("vi", "VN")) as DecimalFormat
        formatter.applyPattern("#,###")
        return formatter.format(price) + " đ"
    }
}
