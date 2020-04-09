package com.sanicorporation.therealsocialnetwork.utils

import android.content.Context
import android.content.SharedPreferences


class Preferences private constructor(){

    companion object {
        const val sharedPrefString: String = "preferences"
        var INSTANCE: Preferences = Preferences()
    }


    fun addString(context: Context, key: String, value:String?){
        val sharedPref = context.getSharedPreferences(sharedPrefString, Context.MODE_PRIVATE).edit()
        with(sharedPref){
            putString(key, value)
            commit()
        }
    }

    fun getString(context: Context, key: String, defaultValue: String? = ""): String?{
        val sharedPref = context.getSharedPreferences(sharedPrefString, Context.MODE_PRIVATE)
        return sharedPref.getString(key,defaultValue)
    }

    fun exist(context: Context, key: String): Boolean{
        val sharedPref = context.getSharedPreferences(sharedPrefString, Context.MODE_PRIVATE)
        return sharedPref.contains(key)
    }

    fun clear(context: Context){
        val sharedPref = context.getSharedPreferences(sharedPrefString, Context.MODE_PRIVATE).edit()
        with(sharedPref){
            clear()
            commit()
        }
    }

    fun delete(context: Context, key: String){
        val sharedPref = context.getSharedPreferences(sharedPrefString, Context.MODE_PRIVATE).edit()
        with(sharedPref){
            remove(key)
            commit()
        }
    }
}