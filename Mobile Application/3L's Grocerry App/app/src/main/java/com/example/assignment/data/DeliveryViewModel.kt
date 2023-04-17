package com.example.assignment.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.Address
import com.example.assignment.App.Companion.db
import kotlinx.coroutines.launch

class DeliveryViewModel: ViewModel() {

    // TODO
    fun getAll() = db.addressDao.getAll()

    // TODO
    fun get(id: Int) = db.addressDao.get(id)

    // TODO
    // for suspend need to put into launch box
    fun insert(f: Address) = viewModelScope.launch { db.addressDao.insert(f) }

    // TODO
    fun update(f: Address) = viewModelScope.launch { db.addressDao.update(f) }

    // TODO
    fun delete(f: Address) = viewModelScope.launch { db.addressDao.delete(f) }

    // TODO
    fun deleteAll() = viewModelScope.launch { db.addressDao.deleteAll() }

    // TODO
    fun validate(f: Address): String {
        var err = ""

        if (f.name == "") {
            err += "- Name is required.\n"
        }

        if (f.phoneNo == "" ) {
            err += "- Phone No is required.\n"
        }

        else if(f.phoneNo.length < 10){
            err += "- The number of character of phone must be 10-14\n"
        }

        if (f.address == "" ) {
            err += "- Address is required.\n"
        }

        return err
    }
}