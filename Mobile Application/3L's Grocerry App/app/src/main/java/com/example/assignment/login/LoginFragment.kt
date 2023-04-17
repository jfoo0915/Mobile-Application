package com.example.assignment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.assignment.R
import com.example.assignment.data.AuthViewModel
import com.example.assignment.databinding.FragmentLoginBinding
import com.example.assignment.errorDialog
import com.example.assignment.hideKeyboard
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val nav by lazy { findNavController() }
    private val auth: AuthViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.btnLogin.setOnClickListener { login() }
        binding.btnSignup.setOnClickListener { nav.navigate(R.id.registerFragment) }
        return binding.root
    }

    private fun login() {
        hideKeyboard()

        val cusId = auth.getCustomer()?.id
        val ctx = requireContext()
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        val remember = binding.chkRemember.isChecked

        lifecycleScope.launch {
            val success = auth.login(ctx, email, password, remember)
            if (success) {
                nav.popBackStack(R.id.homeFragment, false)
                nav.navigateUp()
            }
            else {
                errorDialog("Invalid login credentials.")
            }
        }

        binding.btnLogin.setOnClickListener { nav.navigate(R.id.homeFragment, bundleOf ("id" to cusId)) }

    }

}