package com.example.msololife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.msololife.auth.IntroActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = Firebase.auth

        if(auth.currentUser == null){
            Log.d("splashActivity", "null")

            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this,IntroActivity::class.java))
                finish()
            }, 2000)

        }else{
            Log.d("splashActivity", "not null")

            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }, 2000)
        }


    }
}