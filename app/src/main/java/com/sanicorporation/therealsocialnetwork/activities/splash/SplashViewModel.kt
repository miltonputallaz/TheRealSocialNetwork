package com.sanicorporation.therealsocialnetwork.activities.splash

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.sanicorporation.therealsocialnetwork.activities.login.LoginActivity


class SplashViewModel : ViewModel() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()


    fun checkLogStatus(handler: (Boolean) ->  Unit){
        if (auth.currentUser != null)
            handler(true)
        else
            handler(false)
    }





}