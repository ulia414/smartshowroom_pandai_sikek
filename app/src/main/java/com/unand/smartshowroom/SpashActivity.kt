package com.unand.smartshowroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class SpashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash)

        //declare the animation
        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)

        val latarSm = findViewById(R.id.latar) as ImageView
        val logoSm = findViewById(R.id.logoSm) as ImageView
        val textSm = findViewById(R.id.textSm) as TextView

        logoSm.startAnimation(stb)
        textSm.startAnimation(ttb)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        logoSm.animate().translationY(4000F).setDuration(1000).setStartDelay(2000);
        textSm.animate().translationY(4000F).setDuration(1000).setStartDelay(2000);
        latarSm.animate().translationY(-4000F).setDuration(1000).setStartDelay(2000);


        Handler().postDelayed(Runnable {
            startActivity(Intent(this, SpashActivity2::class.java))
            finish()
        }, 3000)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}