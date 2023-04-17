package com.example.assignment.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.assignment.*
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProductViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var products = listOf<Product>()
    private val result = MutableLiveData<List<Product>>()
    //filter + sort result

    private var name = ""
    private var categoryId = ""
    private var field = ""
    private var reverse = false

    init {
        viewModelScope.launch {
            //move inside snapshot listener if it will change (eg.admin can add new category)
            val categories = CATEGORIES.get().await().toObjects<Category>()

            PRODUCTS.addSnapshotListener { value, _ ->
                if (value == null) return@addSnapshotListener

                products = value.toObjects<Product>()

                for (p in products) {
                    p.category = categories.find{ it.id == p.categoryId } ?: Category()
                }
                updateResult()
            }

        }

    }


//    suspend fun get(id: String): Product? {
//        return PRODUCTS
//            .document(id)
//            .get()
//            .await()
//            .toObject<Product>()
//    }

    // ---------------------------------------------------------------------------------------------

    // TODO(15): Get search + filter + sort result
    private fun updateResult() {
        var list = products

        // TODO(23): Search + filter
        list = list.filter {
            it.name.contains(name, true) &&
                    (categoryId == "" || categoryId == it.categoryId )
        }

        // TODO(24): Sort
        list = when (field) {
            "id" -> list.sortedBy {it.id}
            "name" -> list.sortedBy {it.name}
            "price" -> list.sortedBy {it.price}
            else -> list
        }
        if(reverse) list = list.reversed()

        result.value = list
    }


    // TODO(30): Return a specific product (from local data)
    fun get(id: String): Product? {

        return products.find { it.id == id }
        //return col.document(id).get().await().toObject<Product>()
    }

    fun getAll() = result


    fun set(p: Product) {
        // TODO
        PRODUCTS.document(p.id).set(p)
    }

    fun delete(id: String) {
        // TODO
        PRODUCTS.document(id).delete()
    }


}




