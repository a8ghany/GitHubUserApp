package com.gnacoding.submissionbfaa.presentation.screen.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.gnacoding.submissionbfaa.databinding.ActivitySplashBinding
import com.gnacoding.submissionbfaa.domain.utils.Constants.SPLASH_TIME_DELAY
import com.gnacoding.submissionbfaa.presentation.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var splashBinding: ActivitySplashBinding
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        splashViewModel.getThemeSetting().observe(this@SplashActivity) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        handler = Handler(mainLooper)
        handler.postDelayed({
            Intent(this, MainActivity::class.java).also { moveToMainActivity ->
                startActivity(moveToMainActivity)
            }
        }, SPLASH_TIME_DELAY)
    }
}