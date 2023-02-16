package com.bignerdranch.logintest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
   lateinit var loginBtn : Button
   lateinit var loginId : EditText
   lateinit var loginPw : EditText
   lateinit var siginUpBtn : TextView
   lateinit var findPw : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginBtn = findViewById(R.id.loginBtn)
        loginId = findViewById(R.id.loginId)
        loginPw = findViewById(R.id.loginPw)
        siginUpBtn = findViewById(R.id.signUpBtn)
        findPw = findViewById(R.id.pwFindBtn)

        siginUpBtn.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            if (loginId.text.isEmpty() && loginPw.text.isEmpty()){
                Toast.makeText(this,"ID와 PW를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

    }
}