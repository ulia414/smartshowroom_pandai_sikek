package com.unand.smartshowroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.unand.smartshowroom.databinding.ActivitySpash3Binding

class SpashActivity3 : AppCompatActivity() {

    private lateinit var binding: ActivitySpash3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpash3Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.regisBtn.setOnClickListener {
            startActivity(Intent(this, DaftarActivity::class.java))
        }
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}