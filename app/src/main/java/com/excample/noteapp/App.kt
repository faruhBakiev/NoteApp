package com.excample.noteapp

import android.app.Application
import com.excample.noteapp.utils.PreferenceHelper

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        PreferenceHelper.unit(this)
    }
}