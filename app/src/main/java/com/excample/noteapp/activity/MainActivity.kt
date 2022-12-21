package com.excample.noteapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.excample.noteapp.R
import com.excample.noteapp.utils.PreferenceHelper

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment

        navController = navHostFragment.navController

        if (PreferenceHelper.signUp && PreferenceHelper.name) {
            navController.navigate(R.id.noteAppFragment)
        } else if (PreferenceHelper.name && !PreferenceHelper.signUp) {
            navController.navigate(R.id.signUpFragment)
        } else {
            navController.navigate(R.id.onBoardFragment)
        }
    }
}