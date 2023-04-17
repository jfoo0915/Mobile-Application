package com.example.assignment.util

import android.media.Rating
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.OrderHis
import com.example.assignment.databinding.OrderHistoryBinding

class RatingAdapter (
    val fn: (ViewHolder, OrderHis) -> Unit = { _, _ -> }
): ListAdapter<OrderHis, RatingAdapter.ViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<OrderHis>() {
        override fun areItemsTheSame(a: OrderHis, b: OrderHis)    = a.id == b.id
        override fun areContentsTheSame(a: OrderHis, b: OrderHis) = a == b
    }

    //bind with order_history template
    class ViewHolder(val binding:OrderHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = OrderHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderHis = getItem(position)

        holder.binding.txtCommentOutput.text = orderHis.comment
        holder.binding.txtOutputProduct.text  = orderHis.product
        holder.binding.txtRatingOutput.text  = orderHis.rating
        holder.binding.txtDateOutput.text  = orderHis.date

        fn(holder, orderHis)
    }
}