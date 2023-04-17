package com.example.assignment.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.assignment.Product
import com.example.assignment.R
import com.example.assignment.data.ProductViewModel
import com.example.assignment.databinding.FragmentProductBinding
import com.example.assignment.util.ProductAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private val nav by lazy { findNavController() }
    private val vm: ProductViewModel by activityViewModels()

    private lateinit var adapter: ProductAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)

        // TODO(29): Default search, filter and sort
        //vm.search("")
        //vm.filter("")
        //sort("id")

        adapter = ProductAdapter { holder, product ->
            //Item click -> navigate to Order Fragment
            holder.binding.root.setOnClickListener() {
                nav.navigate(R.id.orderFragment, bundleOf("id" to product.id))
            }
        }

        binding.rvProduct.adapter = adapter
        binding.rvProduct.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        //Load data
        Firebase.firestore
            .collection("products")
            .get()
            .addOnSuccessListener { snap ->
                val list = snap.toObjects<Product>()
                adapter.submitList(list)
            }


        vm.getAll().observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        // TODO(16): Load products data into recycler view
        //vm.getResult().observe(viewLifecycleOwner) {
        //adapter.submitList(it)
        //    binding.txtProductCount.text = "${it.size} Record(s)"
        //}

       //val arrayAdapter = ArrayAdapter<Category>(requireContext(), android.R.layout.simple_spinner_item)
        //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //binding.spnProduct.adapter = arrayAdapter

        // TODO(18): Load categories data into spinner -> launch block
        //lifecycleScope.launch {
        //    val categories = vm.getCategories()
        //    arrayAdapter.add(Category("", "All"))
        //    arrayAdapter.addAll(categories)
        //}


        return binding.root
    }

}


