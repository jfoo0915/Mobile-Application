package com.example.assignment.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.CART
import com.example.assignment.Cart
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CartViewModel : ViewModel() {
    private var cart = listOf<Cart>()
    private val result = MutableLiveData<List<Cart>>()

    //private var name = ""
    //private var price = ""

    init {
        viewModelScope.launch {
            val cart = CART
                .get()
                .await()
                .toObjects<Cart>()

            CART.addSnapshotListener { value, _ ->
                if (value == null) return@addSnapshotListener

                //cart = value.toObjects<Cart>()


            }
        }
    }

    // TODO(30): Return a specific product (from local data)
    fun get(id: String): Cart? {

        return cart.find { it.id == id }
        //return col.document(id).get().await().toObject<Product>()
    }

    fun getAll() = result

    fun set(ct: Cart){
        CART.document(ct.id).set(ct)
    }

    fun delete(id: String) {
        // TODO
        CART.document(id).delete()
    }

}