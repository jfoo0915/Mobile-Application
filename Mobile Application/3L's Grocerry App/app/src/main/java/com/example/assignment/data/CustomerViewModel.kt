package com.example.assignment.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.CUSTOMER
import com.example.assignment.Customer
import com.example.assignment.ORDERHIS
import com.example.assignment.OrderHis
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CustomerViewModel: ViewModel() {
    val customer = MutableLiveData<List<Customer>>()
    private val customerLive = MutableLiveData<Customer?>()
    private val col = Firebase.firestore.collection("customer")
    init {
        col.addSnapshotListener { value, _ -> customer.value = value?.toObjects() }
    }

    fun get(id: String) = customer.value?.find{it.id == id}

    fun set(f: Customer) {
        col.document(f.id).set(f)
    }
    fun init() = Unit //void

    fun getCustomerLiveData(): LiveData<Customer?> {
        return customerLive
    }

}