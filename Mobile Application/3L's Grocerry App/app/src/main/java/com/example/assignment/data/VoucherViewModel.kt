package com.example.assignment.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class VoucherViewModel:ViewModel() {

    private val vouchers = Firebase.firestore.collection("vouchers")
    private val voucher = MutableLiveData<List<Vouchers>>()
    val customer = MutableLiveData<List<Customer>>()
    // Return a specific customer
    suspend fun get(id: String): Vouchers? {
        return VOUCHERS
            .document(id)
            .get()
            .await() //wait result to return
            .toObject<Vouchers >()
    }

    fun getVoucher(id: String) = voucher.value?.find { it.id == id   }
//
//    suspend fun getVoc(id: String): Voucher {
//        val voucher1 = VOUCHER
//            .whereEqualTo("id",id)
//            .get()
//            .await()
//            .toObjects<Voucher>()
//        val voucher = get(id)
//        for(f in voucher1){
//            f.id = voucher.toString()
//        }
//        return voucher1
//    }


}