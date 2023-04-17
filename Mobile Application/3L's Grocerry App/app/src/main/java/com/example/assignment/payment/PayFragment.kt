package com.example.assignment.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.assignment.R
import com.example.assignment.data.AuthViewModel
import com.example.assignment.data.CustomerViewModel
import com.example.assignment.databinding.FragmentPayBinding
import com.example.assignment.databinding.FragmentPaymentBinding
import com.example.assignment.errorDialog

class PayFragment : Fragment() {
    private lateinit var binding: FragmentPayBinding
    private val nav by lazy {  findNavController() }
    private val vm: CustomerViewModel by activityViewModels()
    private val auth: AuthViewModel by activityViewModels()
    private val total by lazy { arguments?.getDouble("total", 0.0)!! }
    //TODO***
    private val productName by lazy { arguments?.getString("productName", "")}
    //TODO***
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentPayBinding.inflate(inflater, container, false)

        // TODO
        val customer = auth.getCustomer()
        val f = vm.get(customer?.id!!)
        val balance = f?.balance.toString().toDoubleOrNull()?:0.00
        binding.txtCurrentBalance.text = "RM %.2f".format(balance)

        //TODO

        binding.txtPaymentOutput.text = "RM %.2f".format(total)

        if(total < balance) binding.btnPaymentTopUp.isEnabled = false
        else binding.btnPaymentTopUp.setOnClickListener {
            nav.navigate(R.id.topUpFragment, bundleOf("total" to total, "amount" to balance))
        }

        binding.btnPay.setOnClickListener {
            if(total > balance){
                errorDialog(getString(R.string.the_balance_is_insufficient))
            }
            else{
                //TODO***
                nav.navigate(R.id.topUp2Fragment, bundleOf("total" to total, "amount" to balance,"productName" to productName))
                //TODO***
            }
        }
        return binding.root
    }
}