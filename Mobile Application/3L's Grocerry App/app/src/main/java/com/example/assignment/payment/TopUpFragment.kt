package com.example.assignment.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.text.isDigitsOnly
import androidx.navigation.fragment.findNavController
import com.example.assignment.R
import com.example.assignment.databinding.FragmentTopUpBinding
import com.example.assignment.errorDialog

class TopUpFragment : Fragment() {
    private lateinit var binding: FragmentTopUpBinding
    private val nav by lazy {  findNavController() }
    private val amount by lazy { arguments?.getDouble("amount", 0.0)!! }
    private val total by lazy { arguments?.getDouble("total", 0.0)!! }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View {
        binding = FragmentTopUpBinding.inflate(inflater, container, false)

        binding.btnTopUpNext.setOnClickListener {submit() }

        binding.btnRM5.setOnClickListener { fillUp(5.00) }
        binding.btnRM10.setOnClickListener { fillUp(10.00) }
        binding.btnRM15.setOnClickListener { fillUp(15.00) }
        binding.edtTopUp.requestFocus()

        return binding.root
    }

    private fun fillUp(i: Double) {
        binding.edtTopUp.setText("%.2f".format(i)).toString().toDoubleOrNull()
    }

    private fun submit() {
        //input
        val topUpAmount = binding.edtTopUp.text.toString().toDoubleOrNull() ?: 0.00

        //validation
        if (binding.edtTopUp.text.toString() == "" ) {
            errorDialog(getString(R.string.please_input_an_amount))
            return
        }

        if(amount > 0 || total > 0){
            nav.navigate(R.id.action_topUpFragment_to_topUp2Fragment,
                bundleOf("amount" to amount,
                    "topUpAmount" to topUpAmount,
                    "total" to total))
        }else{
            nav.navigate(R.id.action_topUpFragment_to_topUp2Fragment,
                bundleOf(
                    "topUpAmount" to topUpAmount))
        }
    }
}