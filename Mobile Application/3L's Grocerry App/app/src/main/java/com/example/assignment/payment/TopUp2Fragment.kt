package com.example.assignment.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.assignment.Customer
import com.example.assignment.OrderHis
import com.example.assignment.R
import com.example.assignment.data.AuthViewModel
import com.example.assignment.data.CustomerViewModel
import com.example.assignment.data.RatingViewModel
import com.example.assignment.databinding.FragmentTopUp2Binding
import com.example.assignment.errorDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*

class TopUp2Fragment : Fragment() {
    private lateinit var binding: FragmentTopUp2Binding
    private val nav by lazy { findNavController() }
    private val amount by lazy { arguments?.getDouble("amount", 0.0)!! }
    private val total by lazy { arguments?.getDouble("total", 0.0)!! }
    private val topUpAmount by lazy { arguments?.getDouble("topUpAmount", 0.0)!! }
    private val vm: CustomerViewModel by activityViewModels()
    private val auth: AuthViewModel by activityViewModels()
    private val orderHis: RatingViewModel by activityViewModels()
    //TODO***
    private val productName by lazy { arguments?.getString("productName", "")}
    //TODO***

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentTopUp2Binding.inflate(inflater, container, false)

        //if(pass word correct)
        //pass amount to balance

        binding.edtPassword.requestFocus()

        //TODO
        val customer = auth.getCustomer()
        val f = vm.get(customer?.id!!)
        var password = f?.password

        //validation
        binding.btnTopUpNext1.setOnClickListener {

            if (binding.edtPassword.text.toString() == "" ) {
                errorDialog(getString(R.string.please_input_a_password))
            }
            else if (binding.edtPassword.text.toString() == password){
                if(total <= 0 || amount <= 0){
                    val balance = f?.balance?.plus(topUpAmount)
                    val c = Customer(
                        id = f?.id.toString(),
                        balance = balance.toString().toDoubleOrNull()?:0.0,
                        name = f?.name.toString(),
                        email = f?.email.toString(),
                        password = f?.password.toString(),
                        coin = f?.coin.toString().toIntOrNull()?:0,
                    )
                    vm.set(c)
                    nav.navigate(R.id.action_global_homeFragment)
                    toast(getString(R.string.top_up_successful))
                }
                else if(total > amount){
                    val balance = f?.balance?.plus(topUpAmount)
                    val c = Customer(
                        id = f?.id.toString(),
                        balance = balance.toString().toDoubleOrNull()?:0.0,
                        name = f?.name.toString(),
                        email = f?.email.toString(),
                        password = f?.password.toString(),
                        coin = f?.coin.toString().toIntOrNull()?:0,
                    )
                    vm.set(c)
                    nav.navigate(R.id.action_global_payFragment)
                    toast(getString(R.string.top_up_successful))
                }
                else if(total <= amount){
                    val balance = f?.balance?.minus(total)
                    val c = Customer(
                        id = f?.id.toString(),
                        balance = balance.toString().toDoubleOrNull()?:0.0,
                        name = f?.name.toString(),
                        email = f?.email.toString(),
                        password = f?.password.toString(),
                        coin = f?.coin.toString().toIntOrNull()?:0,
                    )
                    vm.set(c)
                    nav.navigate(R.id.action_global_homeFragment)
                    toast(getString(R.string.payment_successful))
                    //TODO***
                    val format = SimpleDateFormat("dd-MM-yyyy")
                    val t = format.format(Calendar.getInstance().time)
                    val o = OrderHis(
                        customerId = f?.id.toString(),
                        date = t.toString(),
                        product = productName.toString(),
                        //confirmPassword = binding.edtConfirm.text.toString().trim(),
                    )
                    Firebase.firestore
                        .collection("orderHis")
                        .document()
                        .set(o)
                    //TODO***
                }
            }
            else{
                errorDialog(getString(R.string.the_password_is_incorrect))
            }
        }
        return binding.root

        }
    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }



}