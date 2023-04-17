package com.example.assignment.delivery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.assignment.Address
import com.example.assignment.R
import com.example.assignment.data.DeliveryViewModel
import com.example.assignment.databinding.FragmentDeliveryAddBinding
import com.example.assignment.errorDialog

class DeliveryAddFragment : Fragment() {
    private lateinit var binding: FragmentDeliveryAddBinding
    private val nav by lazy {  findNavController() }
    private val vm: DeliveryViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View{
        binding = FragmentDeliveryAddBinding.inflate(inflater, container, false)
        binding.btnDeliveryAdd.setOnClickListener {
            submit()
        }
        binding.edtName.requestFocus()
        return binding.root
    }

    private fun submit() {
        val f = Address(
            name = binding.edtName.text.toString().trim(),
            phoneNo = binding.edtPhoneNo.text.toString().trim(),
            address = binding.edtAddress.text.toString().trim(),
          //  isSelected = false
        )
        //validation
        val err = vm.validate(f)
        if(err != ""){
            errorDialog(err)
            return
        }

        vm.insert(f)
        nav.navigate(R.id.action_deliveryAddFragment_to_deliveryFragment)
        toast(getString(R.string.added_successfully))
    }
    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}