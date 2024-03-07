package com.farisafra.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.farisafra.githubuser.data.SettingPreferences
import com.farisafra.githubuser.data.dataStore
import com.farisafra.githubuser.data.viewmodel.MainViewModel
import com.farisafra.githubuser.data.viewmodel.ViewModelFactory
import com.farisafra.githubuser.databinding.ActivitySplashScreenBinding
import com.farisafra.githubuser.databinding.ActivityThemeBinding


private lateinit var binding: ActivitySplashScreenBinding

private lateinit var bindingSetting: ActivityThemeBinding
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        }, 3000)

        getThemeSettings()
    }

    private fun getThemeSettings() {
        bindingSetting = ActivityThemeBinding.inflate(layoutInflater)

        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            MainViewModel::class.java
        )

        mainViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    bindingSetting.switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    bindingSetting.switchTheme.isChecked = false
                }
            })
    }

}