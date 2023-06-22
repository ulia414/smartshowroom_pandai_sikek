package com.unand.smartshowroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import com.unand.smartshowroom.databinding.ActivityDaftarBinding

class DaftarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarBinding
    private lateinit var emailTil: TextInputLayout
    private lateinit var emailEt: EditText
    private lateinit var nameTil: TextInputLayout
    private lateinit var nameEt: EditText
    private lateinit var passTil: TextInputLayout
    private lateinit var passEt: EditText
    private lateinit var passKonfTil: TextInputLayout
    private lateinit var passKonfEt: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emailTil = findViewById(R.id.emailTil)
        emailEt = findViewById(R.id.emailEt)
        nameTil = findViewById(R.id.nameTil)
        nameEt = findViewById(R.id.nameEt)
        passTil = findViewById(R.id.passTil)
        passEt = findViewById(R.id.passEt)
        passKonfTil = findViewById(R.id.passKonfTil)
        passKonfEt = findViewById(R.id.passKonfEt)
        registerButton = findViewById(R.id.registerButton)




        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        //handle back button click,
        binding.backBtn.setOnClickListener {
            onBackPressed()// goto previous scren
        }
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)


    }
}