package com.sanicorporation.therealsocialnetwork.activities.register

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.sanicorporation.therealsocialnetwork.activities.login.LoginActivity


class RegisterViewModel : ViewModel() {

    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var confirmPassword: MutableLiveData<String> = MutableLiveData()

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()


    fun onClickRegister(view : View){
        if (validateInputs(email.value, password.value, confirmPassword.value)){
            auth.createUserWithEmailAndPassword(email.value!!, password.value!!)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        goToLogin(view.context)
                    } else {
                        Log.d("","")
                    }
            }
        }
    }

    private fun goToLogin(context: Context?) {
        if (context != null){
            val intent : Intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    private fun validateInputs(email : String?, pass : String?, confirm_pass : String?) : Boolean {
        return passwordAreValid(pass, confirm_pass) && emailIsValid(email)
    }

    private fun emailIsValid(email : String?): Boolean{
        return email != null && email.length > 9
    }

    private fun passwordAreValid(pass : String?, confirm_pass : String?) : Boolean{
        return passwordIsValid(pass) && passwordIsValid(confirm_pass) && pass == confirm_pass
    }

    private fun passwordIsValid(pass : String?) : Boolean {
        return pass != null && pass.length >= 6
    }



}