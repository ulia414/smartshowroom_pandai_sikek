package com.unand.smartshowroom

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.android.material.textfield.TextInputLayout
import com.unand.smartshowroom.databinding.ActivityDaftarBinding
import java.util.regex.Pattern

class DaftarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarBinding
    private lateinit var emailTil: TextInputLayout
    private lateinit var emailEt: EditText
    private lateinit var nameTil: TextInputLayout
    private lateinit var nameEt: EditText
    private lateinit var passTil: TextInputLayout
    private lateinit var passEt: EditText
    private lateinit var registerButton: Button


    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //init firebase auth
        //handle back button click,
        binding.backBtn.setOnClickListener {
            onBackPressed()// goto previous scren
        }
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.loginTextView.setOnClickListener {
           val intent = Intent(this, LoginActivity::class.java)
           startActivity(intent)
       }

        emailTil = findViewById(R.id.emailTil)
        emailEt = findViewById(R.id.emailEt)
        nameTil = findViewById(R.id.nameTil)
        nameEt = findViewById(R.id.nameEt)
        passTil = findViewById(R.id.passTil)
        passEt = findViewById(R.id.passEt)
        registerButton = findViewById(R.id.registerButton)

        binding.registerButton.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val name = binding.nameEt.text.toString()
            val password = binding.passEt.text.toString()

            //validasi email
            if (email.isEmpty()){
                binding.emailEt.error = "Email harus Diisi"
                binding.emailEt.requestFocus()
                return@setOnClickListener
            }
            //validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.emailEt.error = "Email Tidak Valid"
                binding.emailEt.requestFocus()
                return@setOnClickListener
            }

            //validasi password
            if (password.isEmpty()){
                binding.passEt.error = "Kata Sandi harus Diisi"
                binding.passEt.requestFocus()
                return@setOnClickListener
            }
            //validasi panjang password
            if (password.length < 8){
                binding.passEt.error = "Kata Sandi Minimal 8 Karakter"
                binding.passEt.requestFocus()
                return@setOnClickListener
            }
            DaftarFirebase(email,name,password)
        }
    }

    private fun DaftarFirebase(email: String, name: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Daftar berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}