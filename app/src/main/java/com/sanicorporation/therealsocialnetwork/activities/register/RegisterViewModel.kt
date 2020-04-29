package com.sanicorporation.therealsocialnetwork.activities.register

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.sanicorporation.therealsocialnetwork.activities.login.LoginActivity
import com.sanicorporation.therealsocialnetwork.utils.ValidationUtil


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

    private fun validateInputs(email : String?, pass : String?, confirmPassword : String?) : Boolean {
        return ValidationUtil.passwordAreValid(pass, confirmPassword) && ValidationUtil.emailIsValid(email)
    }





}