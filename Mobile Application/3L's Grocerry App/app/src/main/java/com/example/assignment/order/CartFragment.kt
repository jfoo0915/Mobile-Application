package com.example.assignment.order

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.assignment.Cart
import com.example.assignment.data.CartViewModel
import com.example.assignment.databinding.FragmentCartBinding
import com.example.assignment.databinding.FragmentHomeBinding
import com.example.assignment.databinding.ViewCartBinding
import com.example.assignment.util.CartAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val nav by lazy { findNavController() }
    private val vm: CartViewModel by activityViewModels()

    //private val id by lazy { requireArguments().getString("id") ?: "" }


    private lateinit var adapter: CartAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentCartBinding.inflate(inflater, container, false)
        //binding.icDlt.setOnClickListener { delete() }
        //binding.icAddCart.setOnClickListener { addCart() }
        //binding.icMinusCart.setOnClickListener { minusCart() }
        binding.btnOrder.setOnClickListener {  }


        val adapter = CartAdapter { holder, cart ->
            //Delete button click

            holder.binding.icDelete.setOnClickListener {
                vm.delete(cart.id)
            }
        }
        binding.rvCart.adapter = adapter
        binding.rvCart.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))


        //Load Data
        Firebase.firestore
            .collection("cart")
            .get()
            .addOnSuccessListener { snap ->
                val list = snap.toObjects<Cart>()
                adapter.submitList(list)
                //binding.txtCount.text = " ${list.size} cart(s)"
                //binding.txtDiscount =
            }

        vm.getAll().observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        return binding.root
    }

    private fun minusCart() {
        TODO("Not yet implemented")
    }

    private fun addCart() {
        TODO("Not yet implemented")
    }

    private fun delete(id: String){
        Firebase.firestore
            .collection("cart")
            .document(id)
            .delete()
    }



}
