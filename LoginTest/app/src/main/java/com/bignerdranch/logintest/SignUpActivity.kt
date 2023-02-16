package com.bignerdranch.logintest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    lateinit var signUpId : EditText
    lateinit var signUpPw: EditText
    lateinit var signUpName : EditText
    lateinit var signUpAccessBtn : Button
    val api = API.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpId = findViewById(R.id.signUpId)
        signUpPw = findViewById(R.id.signUpPw)
        signUpName = findViewById(R.id.signUpName)
        signUpAccessBtn = findViewById(R.id.signUpAccessBtn)

        signUpAccessBtn.setOnClickListener {
            if (signUpId.text.isEmpty() || signUpPw.text.isEmpty() || signUpAccessBtn.text.isEmpty()){
                Toast.makeText(this,"값을 모두 입력해주세요",Toast.LENGTH_SHORT).show()
            }else{
                val userId : String = signUpId.text.toString()
                val userPw : String = signUpPw.text.toString()
                val userName : String = signUpName.text.toString()
                val userInfo = JoinMember(memberId = userId, memberName = userName, memberPwd = userPw)


                Log.d("log",userInfo.toString())

                api.postUser(userInfo).enqueue(object : Callback<String>{
                    override fun onResponse(
                        call: Call<String>,
                        response: Response<String>
                    ) {
                        if (!response.isSuccessful){
                            Log.d("log", "Code ${response.code()}")
                            Log.d("log", response.body().toString())

                        }else{

                            Log.d("log", response.toString())
                            Log.d("log", response.body().toString())
                            if(response.body().toString().isNotEmpty())
                                Toast.makeText(applicationContext, response.body().toString(), Toast.LENGTH_SHORT).show()
                        }

                    }
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("log", t.message.toString())
                        Log.d("log","fail")
                    }
                })
            }
        }
    }
}