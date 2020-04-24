package com.sanicorporation.therealsocialnetwork.activities.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sanicorporation.therealsocialnetwork.R
import com.sanicorporation.therealsocialnetwork.activities.BaseActivity
import com.sanicorporation.therealsocialnetwork.databinding.ActivityRegisterBinding

class RegisterActivity : BaseActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_register)
        registerViewModel = RegisterViewModel()
        binding.viewmodel = registerViewModel
        binding.activity = this
    }
}
