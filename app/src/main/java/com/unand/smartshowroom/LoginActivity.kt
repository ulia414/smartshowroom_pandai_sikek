package com.unand.smartshowroom

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.unand.smartshowroom.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var tilUsername: TextInputLayout
    private lateinit var etUsername: EditText
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnFacebook: Button
    private lateinit var btnGoogle: Button
    private lateinit var btnApple: Button
    private lateinit var progressDialog: ProgressDialog
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    //constants
    private companion object{
        private const val RC_SIGN_IN = 100
        const val TAG = "GOOGLE_SIGN_IN_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Silahkan Tunggu")
        progressDialog.setCanceledOnTouchOutside(false)

        tilUsername = findViewById(R.id.tilUsername)
        etUsername = findViewById(R.id.etUsername)
        tilPassword = findViewById(R.id.tilPassword)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnFacebook = findViewById(R.id.btnFacebook)
        btnGoogle = findViewById(R.id.btnGoogle)
        btnApple = findViewById(R.id.btnApple)

        //handle back button click,
        binding.backBtn.setOnClickListener {
            onBackPressed()// goto previous scren
        }


        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        //Bagian Login dengan Google
        //configure the google signIn
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.btnGoogle.setOnClickListener {
            //begin Google SignIn
            Log.d(TAG, "onCreate: begin Google SignIn")

            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        //login dengan email dan password
        binding.btnLogin.setOnClickListener {
            val email = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            //validasi email
            if (email.isEmpty()){
                binding.etUsername.error = "Email harus Diisi"
                binding.etUsername.requestFocus()
                return@setOnClickListener
            }
            //validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.etUsername.error = "Email Tidak Valid"
                binding.etUsername.requestFocus()
                return@setOnClickListener
            }

            //validasi password
            if (password.isEmpty()){
                binding.etPassword.error = "Kata Sandi harus Diisi"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }
            //validasi panjang password
            if (password.length < 8){
                binding.etPassword.error = "Kata Sandi Minimal 8 Karakter"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }
            LoginFirebase(email,password)
        }
    }

    private fun LoginFirebase(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Selamat Datang $email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN){
            Log.d(TAG, "onActivityResult: Google Sign intent result")
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                //google SingIn Success, now auth with firebase
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            }
            catch (e: ApiException){
                //failed google SignIn
                Log.d(TAG, "onActivityResult: ${e.message}")
            }
        }
    }
    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        Log.d(TAG, "firebaseAuthWithGoogleAccount: begin firebase auth with google account")

        val credential = GoogleAuthProvider.getCredential(account!!.idToken,null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                //login success
                Log.d(TAG, "firebaseAuthWithGoogleAccount: LoggedIn")

                //get loggedIn user
                val firebaseUser = firebaseAuth.currentUser
                //get user info
                val uid = firebaseUser!!.uid
                val email = firebaseUser.email

                Log.d(TAG, "firebaseAuthWithGoogleAccount: Uid : $uid")
                Log.d(TAG, "firebaseAuthWithGoogleAccount: Email : $email")

                //check if user is new or existing
                if (authResult.additionalUserInfo!!.isNewUser){
                    Log.d(TAG, "firebaseAuthWithGoogleAccount: Akun telah dibuat.. \n$email")
                    Toast.makeText(this@LoginActivity, "Akun telah dibuat... \n$email", Toast.LENGTH_SHORT).show()
                }
                else{
                    //existing user - loggedOn
                    Log.d(TAG, "firebaseAuthWithGoogleAccount: Yang ada... \n$email")
                    Toast.makeText(this@LoginActivity, "Masuk... \n$email", Toast.LENGTH_SHORT).show()
                }
                //start profile activity
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener{ e ->
                //login failed
                Log.d(TAG, "firebaseAuthWithGoogleAccount: Masuk Gagal karena ${e.message}")
                Toast.makeText(this@LoginActivity, "Masuk Gagal karena ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }

}