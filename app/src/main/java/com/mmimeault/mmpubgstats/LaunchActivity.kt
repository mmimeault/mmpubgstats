package com.mmimeault.mmpubgstats

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed(
                { startActivity(Intent(this, MainActivity::class.java)) },
                UI_ANIMATION_DELAY.toLong()
        )
    }

    companion object {
        private val UI_ANIMATION_DELAY = 2000
    }
}
