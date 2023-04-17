package com.example.assignment.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.assignment.CUSTOMER
import com.example.assignment.Customer
import com.example.assignment.R
import com.example.assignment.data.AuthViewModel
import com.example.assignment.data.CustomerViewModel
import com.example.assignment.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentPaymentBinding
    private val nav by lazy {  findNavController() }
    private val vm: CustomerViewModel by activityViewModels()
    private val auth: AuthViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)


        val amount = 0.0
        val total = 0.0
        binding.btnTopUp.setOnClickListener {
            nav.navigate(R.id.action_paymentFragment_to_topUpFragment, bundleOf("amount" to amount,
            "total" to total ))
        }

        // read balance from firebase
        //TODO
        val customer = auth.getCustomer()
         read(customer?.id!!)

        return binding.root
    }

    private fun read(text: String) {
        val f = vm.get(text)
        val balance = f?.balance.toString().toDoubleOrNull()?:0.00
        binding.txtBalanceOutput.text = "RM %.2f".format(balance)
    }


}