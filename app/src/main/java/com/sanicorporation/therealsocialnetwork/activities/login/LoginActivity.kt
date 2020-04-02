package com.sanicorporation.therealsocialnetwork.activities.login

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.sanicorporation.therealsocialnetwork.R
import com.sanicorporation.therealsocialnetwork.activities.main.MainActivity
import com.sanicorporation.therealsocialnetwork.activities.register.RegisterActivity
import com.sanicorporation.therealsocialnetwork.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private var loginViewModel: LoginViewModel = LoginViewModel()


    private val handler: () -> Unit = {
        goToMain()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_login)
        setUpBinding()
    }

    private fun setUpBinding(){
        binding.viewmodel = loginViewModel
        binding.handler = this
        binding.lifecycleOwner = this
    }

    fun onClickRegister(){
        var intent : Intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onClickLogin(){
        loginViewModel.performLogin(handler)
    }


    private fun goToMain(){
        val intent : Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }




}
