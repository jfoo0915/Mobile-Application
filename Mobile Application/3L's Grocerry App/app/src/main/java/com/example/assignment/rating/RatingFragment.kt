package com.example.assignment.rating

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.assignment.OrderHis
import com.example.assignment.R
import com.example.assignment.data.AuthViewModel
import com.example.assignment.data.RatingViewModel
import com.example.assignment.databinding.FragmentRatingBinding
import com.example.assignment.util.RatingAdapter
import kotlinx.coroutines.launch

class RatingFragment : Fragment() {
    private lateinit var binding: FragmentRatingBinding
    private val nav by lazy { findNavController()}
    private val vm: RatingViewModel by activityViewModels()
    private val id by lazy { arguments?.getString("id") ?: "" }
    private lateinit var builder: AlertDialog.Builder
    private val auth: AuthViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentRatingBinding.inflate(inflater, container, false)
        builder = AlertDialog.Builder(context)

        //recycler view
        val adapter = RatingAdapter(){ holder, f ->
            holder.binding.root.setOnClickListener      { nav.navigate(R.id.rateProductFragment, bundleOf("id" to f.id)) }
            holder.binding.btnRateProductEdit.setOnClickListener   { nav.navigate(R.id.rateProductFragment, bundleOf("id" to f.id)) }
            holder.binding.btnRateProductDelete.setOnClickListener { alert(f)

            }
        }
        binding.rvRating.adapter = adapter
        binding.rvRating.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        //launch all the orderhistory under specific customer
        lifecycleScope.launch {
            //TODO
            val customer = auth.getCustomer()
            val orderHis = vm.getHis(customer?.id!!)
            adapter.submitList(orderHis)
        }


        return binding.root
    }

    private fun alert(f: OrderHis) {
        builder.setTitle(R.string.alert)
            .setMessage(R.string.do_you_want_to_delete)
            .setCancelable(true)
            .setPositiveButton(R.string.yes){
                    dialogInterface, it ->
                vm.delete(f.id)
                nav.navigateUp()
                toast(getString(R.string.delete))
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