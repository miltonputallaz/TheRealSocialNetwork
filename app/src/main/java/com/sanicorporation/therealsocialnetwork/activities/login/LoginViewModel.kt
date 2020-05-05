package com.sanicorporation.therealsocialnetwork.activities.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.sanicorporation.therealsocialnetwork.utils.ValidationUtil


class LoginViewModel : ViewModel() {
    private var email: MutableLiveData<String> = MutableLiveData()
    private var password: MutableLiveData<String> = MutableLiveData()
    private var loading: MutableLiveData<Boolean> = MutableLiveData()

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        loading.value = false
    }

    fun getEmail(): LiveData<String> = email

    fun setEmail(email: String){
        this.email.value = email
    }

    fun password(): LiveData<String> = password

    fun password(pass: String){
        this.password.value = pass
    }

    fun loading(): LiveData<Boolean> = loading


    fun performLogin(handler : (uid:String?)-> Unit, errorHandler: (error: String) -> Unit){

        if (validateInputs(email.value, password.value)){
            showLoading()
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
    }

    private fun validateInputs(email : String?, pass : String?) : Boolean {
        return ValidationUtil.passwordIsValid(pass) && ValidationUtil.emailIsValid(email)
    }

    private fun showLoading(){
        loading.value = true
    }

    private fun hideLoading(){
        loading.value = false
    }





}