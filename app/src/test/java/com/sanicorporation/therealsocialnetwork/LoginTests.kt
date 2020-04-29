package com.sanicorporation.therealsocialnetwork

import com.sanicorporation.therealsocialnetwork.activities.login.LoginViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class LoginTests {


    @Test
    fun validateLogin(){
        var viewmodel = LoginViewModel()
        viewmodel.email.value = "miltonputallaz@gmail.com"
        viewmodel.password.value = "123456"
        viewmodel.performLogin({
            assert(true)
        },{
            assert(false)
        })
    }
}