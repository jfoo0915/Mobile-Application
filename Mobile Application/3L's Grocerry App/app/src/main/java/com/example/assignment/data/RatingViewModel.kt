package com.example.assignment.data

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

class RatingViewModel:ViewModel() {

private val rating = Firebase.firestore.collection("orderHis")
    private val rate = MutableLiveData<List<OrderHis>>()
    // Return a specific customer
    suspend fun get(id: String): Customer? {
        return CUSTOMER
            .document(id)
            .get()
            .await() //wait result to return
            .toObject<Customer >()
    }

    //Return all order history (fruits) under a specific user (category)
    //           Populate [category] field
    suspend fun getHis(id: String): List<OrderHis> {
        val orderHis = ORDERHIS
            .whereEqualTo("customerId",id)
            .get()
            .await()
            .toObjects<OrderHis>()
        val customer = get(id)
        for(f in orderHis){
            f.customer = customer!!
        }
        return orderHis
    }

    fun getAll(id: String) = rate.value?.find{it.id == id}
    fun testing() = rate

    fun delete(id: String) {
        ORDERHIS.document(id).delete()
    }

    suspend fun  getOneOrder (id: String): OrderHis?{
        return rating.document(id).get().await().toObject<OrderHis>()
    }

    fun set(f: OrderHis) {
        rating.document(f.id).set(f)
    }

    fun init() = Unit //void


}