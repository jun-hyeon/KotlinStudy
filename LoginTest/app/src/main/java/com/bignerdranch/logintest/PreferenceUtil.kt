package com.bignerdranch.logintest

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil (context: Context){
    private val prefs : SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getString(key : String, defValue: String) : String{
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String){
        prefs.edit().putString(key,str).apply()

    }
    fun removePrefs(key : String){
        val editor = prefs.edit()
        editor.remove(key)
        editor.apply()
    }
}
