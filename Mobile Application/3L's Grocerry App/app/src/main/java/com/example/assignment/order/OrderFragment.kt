package com.example.assignment.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.assignment.*
import com.example.assignment.data.AuthViewModel
import com.example.assignment.data.ProductViewModel
import com.example.assignment.data.CustomerViewModel
import com.example.assignment.data.VoucherViewModel
import com.example.assignment.databinding.FragmentOrderBinding
import kotlinx.coroutines.launch

class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding
    private val nav by lazy { findNavController() }
    private val vm: ProductViewModel by activityViewModels()
    private val vm2: VoucherViewModel by activityViewModels()
    private val vm3: CustomerViewModel by activityViewModels()
    private val auth: AuthViewModel by activityViewModels()

    private val id by lazy { requireArguments().getString("id") ?: "" }


    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                binding.imgOrder.setImageURI(it.data?.data)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        //binding.icAdd.setOnClickListener { addQuantity() }
        //binding.icMinus.setOnClickListener { minusQuantity() }
        load()
        binding.edtQuantity.requestFocus()

        //testing
        //binding.txtTotalAmount.text = vm2.getVoucher("DELIVERY2")!!.value.toString()

        //testing

        //binding.txtTotalAmount.text = total.toString().trim()
        binding.ckcoutBtn.setOnClickListener {
            val f = vm.get(id)
            var price = f?.price.toString().toDoubleOrNull()?:0.0
            var quantity = binding.edtQuantity.text.toString().toIntOrNull()?:0


            val customer = auth.getCustomer()

            var coinDiscount = 0
            if(binding.chkCoin.isChecked){
                coinDiscount = (vm3.get(customer?.id!!)?.coin?.toInt() ?: 0)/100
            }
            var total = price * quantity - coinDiscount
            //TODO UPDATE***
            val productName = binding.txtOrderName.text.toString()
            nav.navigate(R.id.payFragment,bundleOf("total" to total, "productName" to productName))
            //TODO UPDATE***
        }
        return binding.root
    }


    private fun minusQuantity() {
        TODO("Not yet implemented")
    }

    private fun addQuantity() {
        TODO("Not yet implemented")
    }

    private fun add() {
        val a = Cart(
            categoryId = binding.txtCategory.text.toString().trim(),
            price = binding.txtOrderPrice.text.toString().toDoubleOrNull() ?: 0.0,
            product = binding.txtOrderName.text.toString().trim()
        )
        //quantity = binding.txtQuantity.text.toIntOrNull().trim()

    }


    private fun load() {

        lifecycleScope.launch {
            val f = vm.get(id)

//            val index = (f?.rating.toString().toIntOrNull() ?: 0) - 1
            binding.txtOrderName.text = f?.name.toString().trim()
            binding.txtCategory.text = f?.category.toString().trim()
            binding.txtOrderPrice.text = f?.price.toString().trim()
            binding.imgOrder.setImageBlob(f?.photo!!)

        }
    }


}


/*
    private fun load(p: Product) {

        lifecycleScope.launch {
            val p = vm.get(id)

            if (p == null){
                nav.navigateUp()
                return@launch
            }
            load(p)

        binding.txtOrderName.text = p.name
        binding.txtOrderPrice.text = p.price.toString()
        binding.txtCategory.text = p.categoryId
        binding.imgOrder.setImageBitmap(p.photo.toBitmap())
    }
/*
    private fun add() {
        TODO("Not yet implemented")
        val a = Cart(
            categoryId = binding.txtCategory.text.toString().trim(),
            price = binding.txtOrderPrice.text.toString().toDoubleOrNull() ?:0.0,
            product = binding.txtOrderName.text.toString().trim()
            //quantity = binding.txtQuantity.text.toIntOrNull().trim()

        )
    }

 */
*/

