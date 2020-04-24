package com.sanicorporation.therealsocialnetwork.activities.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sanicorporation.therealsocialnetwork.R
import com.sanicorporation.therealsocialnetwork.activities.BaseActivity
import com.sanicorporation.therealsocialnetwork.activities.login.LoginActivity
import com.sanicorporation.therealsocialnetwork.activities.main.MainActivity

class SplashActivity : BaseActivity() {

    lateinit var splashViewModel: SplashViewModel

    private val handler: (Boolean) -> Unit = { isLoggedIn ->
        if (isLoggedIn)
            goToMain()
        else
            goToLogin()

        finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashViewModel = SplashViewModel()
        splashViewModel.checkLogStatus(handler)
    }

    private fun goToLogin(){
        val intent : Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


    private fun goToMain(){
        val intent : Intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
    }

}
