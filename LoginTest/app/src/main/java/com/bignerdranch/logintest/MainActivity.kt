package com.bignerdranch.logintest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
   lateinit var loginBtn : Button
   lateinit var loginId : EditText
   lateinit var loginPw : EditText
   lateinit var siginUpBtn : TextView
   lateinit var findPw : TextView

   private val api  = API.create()

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
            }else{
                val loginRequest  = LoginRequest(loginId.text.toString(), loginPw.text.toString())
                api.loginMember(loginRequest).enqueue(object : Callback<LoginInfo>{
                    override fun onResponse(call: Call<LoginInfo>, response: Response<LoginInfo>) {


                            Log.d("Login Request success", response.code().toString())
                            Log.d("Login Request success", response.body().toString())
//                            val loginInfo = response.body()?.let {
//                                    it -> LoginInfo( it.memberId, it.memberState, it.memberName) }
                        val loginInfo = response.body()

                        if (loginInfo != null) {
                            Toast.makeText(applicationContext, loginInfo.memberState,Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<LoginInfo>, t: Throwable) {
                        Log.d("Login Request fail", t.message.toString())
                        Log.d("Login Request fail", "fail")
                    }

                })
            }
        }

    }
}