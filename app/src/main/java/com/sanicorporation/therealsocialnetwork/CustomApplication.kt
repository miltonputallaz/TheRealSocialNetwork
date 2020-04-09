package com.sanicorporation.therealsocialnetwork

import android.app.Application
import com.sanicorporation.therealsocialnetwork.utils.Preferences

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val inst  = Preferences.INSTANCE
    }

}