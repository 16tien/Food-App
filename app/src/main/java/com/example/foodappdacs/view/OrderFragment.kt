package com.example.foodappdacs.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.app.utils.PrefManager
import com.example.foodappdacs.adapter.CartAdapter
import com.example.foodappdacs.adapter.OnItemClickListener
import com.example.foodappdacs.adapter.OrderAdapter
import com.example.foodappdacs.api.OrderApi
import com.example.foodappdacs.api.RetrofitClient
import com.example.foodappdacs.databinding.FragmentOrderBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class OrderFragment : Fragment() {
    private lateinit var binding:FragmentOrderBinding
    private lateinit var orderApi: OrderApi
    private lateinit var adapter: OrderAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        loadOrder(PrefManager.getUserId())
        return binding.root
    }

    private fun loadOrder(userId: Int) {
        orderApi= RetrofitClient.createApiOrder()
        CoroutineScope(Dispatchers.IO).launch {
            var orders = orderApi.getOrders(userId)
            requireActivity().runOnUiThread {
                adapter = OrderAdapter(orders)
                binding.recyclerView.adapter = adapter
                adapter.setOnItemClickListener(object : OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        val orderId =orders[position].order_id
                        var intent = Intent(context, OrderDetailActivity::class.java)
                        intent.putExtra("orderId", orderId)
                        startActivity(intent)
                    }
                })
            }

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}