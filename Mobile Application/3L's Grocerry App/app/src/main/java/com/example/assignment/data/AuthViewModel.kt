package com.example.assignment.data

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.assignment.CUSTOMER
import com.example.assignment.Customer
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await
//import javax.crypto.EncryptedPrivateKeyInfo

class AuthViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val customerLiveData = MutableLiveData<Customer?>()
    private var listener: ListenerRegistration? = null

    //Remove snapshot listener when view model is destroyed
    override fun onCleared() {
        super.onCleared()
        listener?.remove()
    }

    //Return observable live data
    fun getCustomerLiveData(): LiveData<Customer?> {
        return customerLiveData
    }

    //Return user from live data
    fun getCustomer(): Customer? {
        return customerLiveData.value
    }

    //Login
    suspend fun login(ctx: Context, email: String, password: String, remember: Boolean = false): Boolean {
        val customer = CUSTOMER
            //.whereEqualTo(FieldPath.documentId(), "xxx")
            .whereEqualTo("email", email)
            .whereEqualTo("password", password)
            .get()
            .await()
            .toObjects<Customer>()
            .firstOrNull() ?: return false

        listener?.remove()
        listener = CUSTOMER.document(customer.id).addSnapshotListener { doc, _ ->
            customerLiveData.value = doc?.toObject()
        }

        if (remember) {
            getPreferences(ctx)
                .edit()
                .putString("email", email)
                .putString("password", password)
                .apply()
        }

        return true
    }
    fun logout(ctx: Context) {
        listener?.remove()
        customerLiveData.value = null

        getPreferences(ctx)
            .edit()
            .clear()
            .apply()
    }

    private fun getPreferences(ctx: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            "AUTH",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            ctx,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    suspend fun loginFromPreferences(ctx: Context) {
        val p = getPreferences(ctx)
        val email = p.getString("email", null)
        val password = p.getString("password", null)

        if(email != null && password != null) {
            login(ctx, email, password)
        }
    }
}
