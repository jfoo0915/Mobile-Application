package com.example.assignment.delivery

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.assignment.*
import com.example.assignment.data.AuthViewModel
import com.example.assignment.data.CustomerViewModel
import com.example.assignment.data.DeliveryViewModel
import com.example.assignment.databinding.FragmentDeliveryBinding
import com.example.assignment.util.AddressAdapter

class DeliveryFragment : Fragment() {
    private lateinit var binding: FragmentDeliveryBinding
    private val nav by lazy {  findNavController() }
    private val vm: DeliveryViewModel by activityViewModels()
    private lateinit var builder: AlertDialog.Builder
    private val vm2: CustomerViewModel by activityViewModels()
    private val deliveryAdd by lazy { arguments?.getInt("id", 0)!! }
    private val auth: AuthViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentDeliveryBinding.inflate(inflater, container, false)
        builder = AlertDialog.Builder(context)
        binding.btnAddAddress.setOnClickListener { nav.navigate(R.id.deliveryAddFragment) }
        val customer = auth.getCustomer()
        val c = vm2.get(customer?.id!!)


        val adapter = AddressAdapter { holder, f ->
            holder.binding.root.setOnClickListener      { nav.navigate(R.id.delivery1Fragment, bundleOf("id" to f.id)) }
            holder.binding.btnEdit.setOnClickListener   { nav.navigate(R.id.delivery1Fragment, bundleOf("id" to f.id)) }
            holder.binding.btnDelete.setOnClickListener {
                alert(f)
            }
           holder.binding.radiobtnCheck.setOnClickListener{
                nav.navigate(R.id.action_global_deliveryFragment, bundleOf("id" to f.id))
                submitted = true
            }


        }
        binding.rv.adapter = adapter

        //divider
        binding.rv.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))

        // Fill RecyclerView with data
        vm.getAll().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        //
        if(submitted){
            //get data
            vm.get(deliveryAdd).observe(viewLifecycleOwner){a ->
                //set data
                val cus = Customer(
                    id = c?.id.toString(),
                    balance = c?.balance.toString().toDoubleOrNull()?:0.0,
                    name = c?.name.toString(),
                    email = c?.email.toString(),
                    password = c?.password.toString(),
                    coin = c?.coin.toString().toIntOrNull()?:0,
                    deliveryAddress = a.address
                )
                //update data
                vm2.set(cus)
                toast(getString(R.string.updated_successfully))
            }
            submitted = false
        }
        return binding.root
    }

    private fun alert(f: Address) {
        builder.setTitle(R.string.alert)
            .setMessage(R.string.do_you_want_to_delete)
            .setCancelable(true)
            .setPositiveButton(R.string.yes){
                dialogInterface, it ->
                vm.delete(f)
            }
            .setNegativeButton(R.string.no){
                dialogInterface, it ->
                dialogInterface.cancel()
            }.show()
    }
    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}