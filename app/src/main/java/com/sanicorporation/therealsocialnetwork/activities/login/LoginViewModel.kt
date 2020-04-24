package com.sanicorporation.therealsocialnetwork.activities.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth


class LoginViewModel : ViewModel() {
    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var showLoading: MutableLiveData<Boolean> = MutableLiveData()

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        showLoading.value = false
    }

    fun performLogin(handler : (uid:String?)-> Unit){
        if (email.value != null && password.value != null ){
            showLoading()
            if (email.value!!.length > 0 && password.value!!.length > 0 ){
                auth.signInWithEmailAndPassword(email.value!!, password.value!!)
                    .addOnCompleteListener { task ->
                        hideLoading()
                        if (task.isSuccessful) {
                            handler(task.result?.user?.uid)
                        } else {
                            Log.d("","")
                        }

                        // ...
                    }
            } else {
            }
        } else {
        }
    }

    fun showLoading(){
        showLoading.value = true
    }

    fun hideLoading(){
        showLoading.value = false
    }





}