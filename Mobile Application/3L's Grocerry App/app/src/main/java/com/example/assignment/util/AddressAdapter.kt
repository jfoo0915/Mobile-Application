package com.example.assignment.util


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.Address
import com.example.assignment.databinding.DeliveryAddressBinding
import com.example.assignment.delivery.DeliveryFragment
import kotlinx.coroutines.handleCoroutineException

class AddressAdapter (
    val fn: (ViewHolder, Address) -> Unit = { _, _ -> } //Unit = void
) : ListAdapter<Address, AddressAdapter.ViewHolder>(DiffCallback) {

    private var  isNew = false
    private var last = -1

    companion object DiffCallback : DiffUtil.ItemCallback<Address>() {
        override fun areItemsTheSame(a: Address, b: Address)    = a.id == b.id
        override fun areContentsTheSame(a: Address, b: Address) = a == b
    }

    inner class ViewHolder(val binding: DeliveryAddressBinding) : RecyclerView.ViewHolder(binding.root)
    {
        init {
            binding.radiobtnCheck.setOnClickListener{
                handleRadioButton(adapterPosition)
            }
        }
    }

    private fun handleRadioButton(adapterPosition: Int) {
        isNew = true
        getItem(last).isSelected = false
        getItem(adapterPosition).isSelected = true
        last = adapterPosition
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DeliveryAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.txtDeliveryName.text  = item.name
        holder.binding.txtDeliveryPhoneNo.text  = item.phoneNo
        holder.binding.txtDeliveryAddress.text  = item.address
        fn(holder, item)

        if(isNew){
            holder.binding.radiobtnCheck.isChecked =item.isSelected
        }else{
            if(holder.adapterPosition == 0 ){
                holder.binding.radiobtnCheck.isChecked = true
                last = 0
            }
        }
    }
}