package com.example.assignment

import android.app.Application
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class App: Application() {
    companion object{
        lateinit var db: DB
    }

    override fun onCreate() {
        super.onCreate()
        db = DB.getInstance(this)

        //Sample data
        GlobalScope.launch {
            db.addressDao.insertAll( //insert multiple items
                Address(1, "Alex", "0123456789","No. 4 Jalan Kambing Kuala Lumpur"),
                Address(2, "Clavin", "0159423625","No. 2 Jalan Kuching Kuala Lumpur"),
                )
        }
    }
}