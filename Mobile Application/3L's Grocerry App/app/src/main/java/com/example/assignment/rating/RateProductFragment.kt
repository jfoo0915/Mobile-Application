package com.example.assignment.rating

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.assignment.OrderHis
import com.example.assignment.R
import com.example.assignment.data.DeliveryViewModel
import com.example.assignment.data.RatingViewModel
import com.example.assignment.databinding.FragmentRateProductBinding
import com.example.assignment.databinding.FragmentRatingBinding
import kotlinx.coroutines.launch

class RateProductFragment : Fragment() {
    private lateinit var binding: FragmentRateProductBinding
    private val nav by lazy { findNavController()}
    private val id by lazy { arguments?.getString("id") ?: "" }
    private val vm: RatingViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentRateProductBinding.inflate(inflater,container, false)

        //receive the data from rate page
        load()

        binding.btnSubmit.setOnClickListener { submit() }
        return binding.root
    }

    private fun load() {

        lifecycleScope.launch {
            val f = vm.getOneOrder(id)

            val index = (f?.rating.toString().toIntOrNull() ?: 0) - 1
            binding.txtRatingProductName.text = f?.product.toString().trim()
            binding.txtRatingProductDate.text = f?.date.toString().trim()
            binding.spnRate.setSelection(index)
            binding.edtRateProductComment.setText(f?.comment)
        }
    }

    private fun submit(){
        lifecycleScope.launch {
            val o = vm.getOneOrder(id)
            val f = OrderHis(
                id = id,
                product = binding.txtRatingProductName.text.toString().trim(),
                date = binding.txtRatingProductDate.text.toString().trim(),
                rating = binding.spnRate.selectedItem.toString().trim(),
                comment = binding.edtRateProductComment.text.toString().trim(),
                customerId = o?.customerId?:"",
            )
            vm.set(f)
            nav.navigateUp()
            toast(getString(R.string.updated_successfully))
        }
    }
    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }


}