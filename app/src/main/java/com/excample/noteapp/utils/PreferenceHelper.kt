package com.excample.noteapp.utils

import android.content.Context
import android.content.SharedPreferences

object PreferenceHelper {

    private lateinit var sharedPreference: SharedPreferences

    fun unit(context: Context) {
        sharedPreference = context.getSharedPreferences("shared", Context.MODE_PRIVATE)
    }

    var name: Boolean
        get() = sharedPreference.getBoolean("text", false)
        set(value) = sharedPreference.edit().putBoolean("text", value).apply()

    var signUp: Boolean
        get() = sharedPreference.getBoolean("txt", false)
        set(value) = sharedPreference.edit().putBoolean("txt", value).apply()

}
