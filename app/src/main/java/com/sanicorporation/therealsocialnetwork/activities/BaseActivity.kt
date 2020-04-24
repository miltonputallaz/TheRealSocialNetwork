package com.sanicorporation.therealsocialnetwork.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.sanicorporation.therealsocialnetwork.R
import com.sanicorporation.therealsocialnetwork.utils.Keys
import com.sanicorporation.therealsocialnetwork.utils.Preferences

open class BaseActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val theme = Preferences.INSTANCE.getString(this, Keys.THEME_ID.toString())
        if (theme == Keys.THEME_LIGHT.toString()){
            setTheme(R.style.LightTheme)
        } else {
            setTheme(R.style.AppTheme)
        }
    }
}