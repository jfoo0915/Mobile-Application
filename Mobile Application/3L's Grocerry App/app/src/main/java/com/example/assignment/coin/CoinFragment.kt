package com.example.assignment.coin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.assignment.Customer
import com.example.assignment.button
import com.example.assignment.count
import com.example.assignment.data.AuthViewModel
import com.example.assignment.data.CustomerViewModel
import com.example.assignment.databinding.FragmentCoinBinding

class CoinFragment : Fragment() {
    private lateinit var binding: FragmentCoinBinding
    private val nav by lazy { findNavController() }
    private val vm: CustomerViewModel by activityViewModels()
    private val auth: AuthViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentCoinBinding.inflate(inflater, container, false)

        //TODO
        val customer = auth.getCustomer()
        read(customer?.id!!)
        if(count > 1){
            button = false
        }
        binding.btnCheckin.isEnabled = button

        binding.btnCheckin.setOnClickListener {
            checkIn()
        }

        return binding.root
    }

    private fun checkIn() {
        val n = (1..20).random()
        var m = binding.txtCoinOutput.text.toString().toIntOrNull()?:0
        var result = n+m
        val customer = auth.getCustomer()
        binding.txtCoinOutput.text = "$result"
        button = false
        count += 1
        binding.btnCheckin.isEnabled = button
        //TODO
        val f = vm.get(customer?.id!!)

        val c = Customer(
            id = f?.id.toString(),
            balance   = f?.balance.toString().toDoubleOrNull()?:0.00,
            name = f?.name.toString(),
            email = f?.email.toString(),
            password = f?.password.toString(),
            coin = result.toString().toIntOrNull()?:0,
            deliveryAddress = f?.deliveryAddress.toString(),
            )

        vm.set(c)
    }

    private fun read(text: String) {
        val f = vm.get(text)
        binding.txtCoinOutput.text = f?.coin.toString()
    }


}