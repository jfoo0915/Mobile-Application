package com.example.assignment.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.Cart
import com.example.assignment.databinding.ViewCartBinding
import com.example.assignment.setImageBlob
import java.text.DecimalFormat

class CartAdapter (
    val ct: (ViewHolder, Cart) -> Unit = { _, _ -> }
    ) :
    ListAdapter<Cart, CartAdapter.ViewHolder>(DiffCallback) {
    private val formatter = DecimalFormat("0.00")

    companion object DiffCallback : DiffUtil.ItemCallback<Cart>() {
        override fun areItemsTheSame(a: Cart, b: Cart) = a.id == b.id

        override fun areContentsTheSame(a: Cart, b: Cart) = a == b
    }

    class ViewHolder(val binding: ViewCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cart = getItem(position)

        //holder.binding.txtProductId.text = product.id
        holder.binding.txtCartName.text = cart.product
        holder.binding.txtCartPrice.text = "RM ${"%.2f".format(cart.price)}"
        holder.binding.txtCartQuantity.text = "${"%d".format(cart.quantity)} left"
        holder.binding.imgCart.setImageBlob(cart.photo)

        ct(holder, cart)


    }
}