package com.example.assignment.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.assignment.Customer
import com.example.assignment.R
import com.example.assignment.databinding.FragmentRegisterBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val nav by lazy { findNavController() }
    //private val vm: AuthViewModel by activityViewModels<> {  }

    //private lateinit var adapter: CustomerAdapter
    //private val launcher = registerForActivityResult(StartActivityForResult()) {
    //    if (it.resultCode == Activity.RESULT_OK) {
    //        binding.imgProtrait.setImageURI(it.data?.data)
    //}
    //}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        reset()
        //TODO
       // binding.imgProtrait.setOnClickListener { select() }
        binding.btnBack.setOnClickListener { nav.navigate(R.id.loginFragment)}
        binding.btnNext.setOnClickListener {
            next()
        }
        binding.btnReset.setOnClickListener { reset() }

        return binding.root
    }

    //private fun select() {
    //    val intent = Intent(Intent.ACTION_GET_CONTENT)
    //    intent.type = "image/*"
    //    launcher.launch(intent)
    //}

    private fun reset() {
        //binding.edtId.text.clear()
        binding.edtEmailRegister.text.clear()
        binding.edtConfirm.text.clear()
        binding.edtRegisPassword.text.clear()
        //binding.imgProtrait.setImageDrawable(null)
        //binding.edtId.requestFocus()
    }

    private fun next() {
        val c = Customer(
            name = binding.edtName.text.toString().trim(),
            email = binding.edtEmailRegister.text.toString().trim(),
            password = binding.edtRegisPassword.text.toString().trim(),
            balance = 0.00,
            coin = 0,
            deliveryAddress = ""
            //confirmPassword = binding.edtConfirm.text.toString().trim(),
        )
        //simple validation
        var confirmPassword: String = ""
        if (binding.edtName.text.toString() == ""){
            toast("Invalid name")
            return
        }

        if (binding.edtEmailRegister.text.toString() == ""){
            toast("Invalid email")
            return
        }

        if (binding.edtRegisPassword.text.toString() == ""){
            toast("Invalid password")
            return
        }

        if (binding.edtConfirm.text.toString() == ""){
            toast("Invalid password")
            return
        }

        if (binding.edtRegisPassword.text.toString() != binding.edtConfirm.text.toString()){
            toast("Please make sure your password is same.")
            return

        }
        Firebase.firestore
            .collection("customer")
            .document()
            .set(c)

        nav.navigate(R.id.loginFragment)
    }

private fun toast(s: String) {
    Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
}
}