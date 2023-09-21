package com.example.matchingapp.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matchingapp.MainActivity
import com.example.matchingapp.R
import com.example.matchingapp.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.loginBtn.setOnClickListener {
            val email = binding.emailArea.text.toString()
            val password = binding.pwdArea.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {task ->
                if(task.isSuccessful){
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Snackbar.make(it,"실패",Snackbar.LENGTH_SHORT).show()
                }
            }
        }


    }
}