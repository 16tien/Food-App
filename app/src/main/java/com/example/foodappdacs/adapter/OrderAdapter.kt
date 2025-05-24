package com.example.foodappdacs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodappdacs.databinding.ItemOrderBinding
import com.example.foodappdacs.model.Order
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class OrderAdapter(private var orders: List<Order>):
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class OrderViewHolder(private var binding: ItemOrderBinding):RecyclerView.ViewHolder(binding.root){
        val tvDate = binding.tvDate
        val tvStatus = binding.tvStatus
        val tvTotal = binding.tvTotal

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.OrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderBinding.inflate(inflater, parent, false)
        return OrderViewHolder(binding)
    }
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.tvDate.text = order.created_at
        when(order.order_status){
            1 -> holder.tvStatus.text ="Đơn hàng đang chờ xử lí"
            2 -> holder.tvStatus.text = "Đơn hàng đang giao"
            3 -> holder.tvStatus.text = "Giao hàng thành công"
        }
        holder.tvTotal.text = getPrice(order.total_price)
    }
    fun getPrice(string: Int): String {
        val formatter = NumberFormat.getInstance(Locale("vi", "VN")) as DecimalFormat
        formatter.applyPattern("#,###")
        return formatter.format(string) + " đ"
    }
    override fun getItemCount()= orders.size
}




