package com.bignerdranch.logintest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.bignerdranch.logintest.repository.FeedRepository


class LoginActivity : AppCompatActivity() {
   lateinit var loginBtn : Button
   lateinit var loginId : EditText
   lateinit var loginPw : EditText
   lateinit var siginUpBtn : TextView
   lateinit var findPw : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn = findViewById(R.id.loginBtn)
        loginId = findViewById(R.id.loginId)
        loginPw = findViewById(R.id.loginPw)
        siginUpBtn = findViewById(R.id.signUpBtn)
        findPw = findViewById(R.id.pwFindBtn)

        val memberId = App.prefs.getString("memberId","no memberId")
        val memberPw = App.prefs.getString("memberPw", "no memberPw")
        Log.d("PREFS","ID: $memberId, PW: $memberPw")

        if (memberId != "no memberId" && memberPw != "no memberPw"){
            val intent = Intent(applicationContext,FeedActivity::class.java)
            startActivity(intent)
            finish()
        }

        siginUpBtn.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            if (loginId.text.isEmpty() && loginPw.text.isEmpty()){
                Toast.makeText(this,"ID와 PW를 입력해주세요", Toast.LENGTH_SHORT).show()
            }else{

                val loginRequest  = LoginRequest(loginId.text.toString(), loginPw.text.toString())
                val loginInfoLiveData = FeedRepository().loginRequest(loginRequest)

                if(loginInfoLiveData.value!!.memberState == "로그인 완료"){

                    App.prefs.setString("memberId", loginRequest.memberId)
                    App.prefs.setString("memberPw", loginRequest.memberPwd)

                    val intent = Intent(this, FeedActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }
        }

    }
}