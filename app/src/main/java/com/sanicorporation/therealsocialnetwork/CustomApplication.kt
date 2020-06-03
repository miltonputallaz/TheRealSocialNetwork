package com.sanicorporation.therealsocialnetwork

import android.app.Application
import com.sanicorporation.therealsocialnetwork.di.component.ApplicationComponent
import com.sanicorporation.therealsocialnetwork.di.component.DaggerApplicationComponent

class CustomApplication : Application() {


    companion object{
        lateinit var instance: Application
        lateinit var component: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        component = DaggerApplicationComponent.builder().application(instance as CustomApplication).build()
    }

}