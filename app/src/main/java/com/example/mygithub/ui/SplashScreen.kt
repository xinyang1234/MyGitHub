package com.example.mygithub.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.mygithub.databinding.ActivitySplashScreenBinding
import com.example.mygithub.prefences.MainViewModel
import com.example.mygithub.prefences.SettingPreferences
import com.example.mygithub.prefences.ViewModelFactory
import com.example.mygithub.prefences.dataStore

class SplashScreen : AppCompatActivity() {

    private lateinit var activitySplashBinding: ActivitySplashScreenBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySplashBinding = ActivitySplashScreenBinding .inflate(layoutInflater)
        setContentView(activitySplashBinding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        viewModel  = ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]
        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                false
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }, _DURATION)
    }

    companion object {
        const val _DURATION = 2000L
    }
}