package com.hafidrf.musicplayer.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.hafidrf.musicplayer.databinding.ActivitySplashBinding
import com.hafidrf.musicplayer.presentation.music.SearchMusicActivity

class SplashActivity : AppCompatActivity() {

    private val binding : ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupStatusBar()
        moveMainActivity()
    }

    private fun setupStatusBar() {
        with(window) {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    private fun moveMainActivity() {
        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, SearchMusicActivity::class.java))
            finish()
        }, 4500)
    }
}