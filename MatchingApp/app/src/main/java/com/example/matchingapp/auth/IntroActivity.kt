package com.example.matchingapp.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.matchingapp.MainActivity
import com.example.matchingapp.R
import com.example.matchingapp.databinding.ActivityIntroBinding
import com.example.matchingapp.utils.FirebaseAuthUtils
import com.google.firebase.auth.FirebaseAuth

class IntroActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityIntroBinding.inflate(layoutInflater)
    }

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        setContentView(binding.root)

        val uid = FirebaseAuthUtils.getUid()
        Log.d("IntroActivity", uid)

        if(uid != "null"){
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }



        binding.joinBtn.setOnClickListener {
            startActivity(Intent(this,JoinActivity::class.java))
        }

        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }
}