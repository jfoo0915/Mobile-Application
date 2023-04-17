package com.example.assignment.data

import androidx.lifecycle.ViewModel
import com.example.assignment.CATEGORIES
import com.example.assignment.Category
import com.example.assignment.PRODUCTS
import com.example.assignment.Product
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects

class CategoryViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    // Dummy function
    fun init() = Unit

    // TODO(7): Return all categories
    //          Populate [count] field
    suspend fun getAll(): List<Category> {
        val categories = CATEGORIES // TODO
            .get()
            .await()
            .toObjects<Category>()

        for (c in categories){
            c.count = PRODUCTS
                .whereEqualTo("categoryId", c.id)
                .get()
                .await()
                .size()
        }
        // TODO

        return categories
    }

    // TODO(10): Return a specific category
    suspend fun get(id: String): Category? {
        return CATEGORIES
            .document(id)   //return only one category based on id
            .get()
            .await()
            .toObject<Category>()
    }

    // TODO(11): Return all fruits under a specific category
    //           Populate [category] field
    suspend fun getFruits(id: String): List<Product> {
        val products = PRODUCTS
            .whereEqualTo("categoryId", id)
            .get()
            .await()
            .toObjects<Product>()

        val category = get(id)

        for (p in products)
        {
            p.category= category!!
        }

        return products
    }

}


