package com.example.assignment.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.Product
import com.example.assignment.databinding.FragmentProductBinding
import com.example.assignment.databinding.FragmentViewProductBinding
import com.example.assignment.setImageBlob
import java.text.DecimalFormat

class ProductAdapter (
    val p: (ViewHolder, Product) -> Unit = { _, _ -> }
    ) :
    ListAdapter<Product, ProductAdapter.ViewHolder>(DiffCallback) {
        private val formatter = DecimalFormat("0.00")

        companion object DiffCallback : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(a: Product, b: Product) = a.id == b.id
            override fun areContentsTheSame(a: Product, b: Product) = a == b
        }

        class ViewHolder(val binding: FragmentViewProductBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = FragmentViewProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val product = getItem(position)

            holder.binding.txtProductName.text = product.name
            holder.binding.txtProductPrice.text = "RM ${"%.2f".format(product.price)}"
            holder.binding.txtProductStock.text = "${"%d".format(product.stock)} left"
            holder.binding.imgProductPhoto.setImageBlob(product.photo)

            // TODO(13): Display [category.name]

            p(holder, product)
        }



    }