package com.example.foodappdacs.view

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.example.foodappdacs.R
import com.example.foodappdacs.databinding.DialogLogginBinding
import com.example.foodappdacs.databinding.DialogOrderBinding
import kotlinx.coroutines.flow.combine

class OrderDialog(
    private val context: Context,
    private val food_id: Int
) {
    val binding = DialogOrderBinding.inflate(LayoutInflater.from(context))
    fun showDialog(){
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        val window =dialog.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setGravity(Gravity.BOTTOM)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val windowAttributes = window.attributes
        windowAttributes.gravity = Gravity.BOTTOM
        window.attributes = windowAttributes
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        binding.btnBuy.setOnClickListener {

        }
        binding.btnIncrease.setOnClickListener {
            increase()
        }
        binding.btnReduce.setOnClickListener {
            reduce()
        }
        binding.btnBuy.setOnClickListener {
            val intent = Intent(context, PayActivity::class.java)
            val qty = binding.tvNumber.text.toString().toInt()
            intent.putExtra("qty", qty)
            intent.putExtra("foodId", food_id)
            context.startActivity(intent)
        }
    }

    private fun reduce() {
        var qty = binding.tvNumber.text.toString().toInt()
        if(qty ==1){
            binding.tvNumber.text = qty.toString()
        }else {
            qty -= 1
            binding.tvNumber.text = qty.toString()
        }
    }

    private fun increase() {
        var qty = binding.tvNumber.text.toString().toInt()
        qty += 1
        binding.tvNumber.text = qty.toString()
    }


}