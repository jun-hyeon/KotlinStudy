package com.example.msololife.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.msololife.MainActivity
import com.example.msololife.R
import com.example.msololife.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        auth = Firebase.auth

        binding.LoginBtn.setOnClickListener {

            val email = binding.emailArea.text.toString()
            val password = binding.passwordArea.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information

                        val intent = Intent(this,MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                        Toast.makeText(this,"로그인 성공",Toast.LENGTH_LONG).show()

                    } else {
                        // If sign in fails, display a message to the user.
                       Toast.makeText(this,"로그인 실패",Toast.LENGTH_LONG).show()
                    }
                }

        }
    }
}