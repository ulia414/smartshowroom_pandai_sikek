package com.unand.smartshowroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.unand.smartshowroom.databinding.ActivitySpash2Binding

class SpashActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivitySpash2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpash2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)

        val logoSm = findViewById(R.id.baju) as ImageView
        val textSm = findViewById(R.id.textOp) as TextView
        logoSm.startAnimation(stb)
        textSm.startAnimation(ttb)


        binding.mulaiBtn.setOnClickListener {
            startActivity(Intent(this, SpashActivity3::class.java))
        }
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}