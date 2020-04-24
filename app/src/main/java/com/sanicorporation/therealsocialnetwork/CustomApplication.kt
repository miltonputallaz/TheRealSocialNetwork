package com.sanicorporation.therealsocialnetwork

import android.app.Application
import com.sanicorporation.therealsocialnetwork.network.BaseService
import com.sanicorporation.therealsocialnetwork.utils.Preferences

class CustomApplication : Application() {


    companion object{
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        val inst  = Preferences.INSTANCE
    }

}