package com.sanicorporation.therealsocialnetwork.activities.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import com.sanicorporation.therealsocialnetwork.R
import com.sanicorporation.therealsocialnetwork.activities.BaseActivity
import com.sanicorporation.therealsocialnetwork.activities.main.MainActivity
import com.sanicorporation.therealsocialnetwork.activities.register.RegisterActivity
import com.sanicorporation.therealsocialnetwork.databinding.ActivityLoginBinding
import com.sanicorporation.therealsocialnetwork.utils.Keys
import com.sanicorporation.therealsocialnetwork.utils.Preferences
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    private var loginViewModel: LoginViewModel = LoginViewModel()


    private val handler: (uid: String?) -> Unit = {
        Preferences.INSTANCE.addString(this, Keys.UID.toString(), it)
        goToMain()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_login)
        ViewCompat.setTranslationZ(progress_bar, 20.0f);

        setUpBinding()
    }

    private fun setUpBinding(){
        binding.viewmodel = loginViewModel
        binding.handler = this
        binding.lifecycleOwner = this
    }

    fun onClickRegister(){
        var intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun onClickLogin(){
        loginViewModel.performLogin(handler)
    }


    private fun goToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }




}
