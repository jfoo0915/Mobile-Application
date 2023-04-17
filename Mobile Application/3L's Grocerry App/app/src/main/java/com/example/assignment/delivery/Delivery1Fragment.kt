package com.example.assignment.delivery

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.assignment.Address
import com.example.assignment.data.DeliveryViewModel
import com.example.assignment.databinding.FragmentDelivery1Binding
import com.example.assignment.errorDialog

class Delivery1Fragment : Fragment() {
    private lateinit var binding: FragmentDelivery1Binding
    private val nav by lazy {  findNavController() }
    private val vm: DeliveryViewModel by activityViewModels()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentDelivery1Binding.inflate(inflater, container, false)
        load()
        binding.btnDeliveryDone.setOnClickListener {submit()}
        return binding.root
    }

    private fun submit() {
        val id = arguments?.getInt("id")?:0
        val f = Address(
            id = id,
            name = binding.edtNameOut.text.toString().trim(),
            phoneNo = binding.edtPhoneOut.text.toString().trim(),
            address = binding.edtDeliveryOut.text.toString().trim(),
        )

        //validation
        val err = vm.validate(f)
        if(err != ""){
            errorDialog(err)
            return
        }

        //update data
        vm.update(f)
        nav.navigateUp() // go back to previous screen
        toast("Updated successfully")
    }

    //get data
    private fun load(){
        val id = arguments?.getInt("id")?:0
        vm.get(id).observe(viewLifecycleOwner){f ->
            if(f == null){
                nav.navigateUp()
                return@observe
            }
           // binding.edtNameOut.text    = f.name

            binding.txtIdOutput.text = f.id.toString().trim()
            binding.edtNameOut.setText(f.name)
            binding.edtPhoneOut.setText(f.phoneNo)
            binding.edtDeliveryOut.setText(f.address)
            binding.edtNameOut.requestFocus()
        }
    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}