package com.bignerdranch.logintest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.bignerdranch.logintest.repository.FeedRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpId : EditText
    private lateinit var signUpPw: EditText
    private lateinit var signUpName : EditText
    private lateinit var signUpAccessBtn : Button
    private lateinit var idCheckBtn : TextView
//    private val api = API.create()
    private var isCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpId = findViewById(R.id.signUpId)
        signUpPw = findViewById(R.id.signUpPw)
        signUpName = findViewById(R.id.signUpName)
        signUpAccessBtn = findViewById(R.id.signUpAccessBtn)
        idCheckBtn = findViewById(R.id.idCheckBtn)



        idCheckBtn.setOnClickListener {


//            api.getRepetitionCheckId(signUpId.text.toString()).enqueue(object : Callback<String>{
//
//                override fun onResponse(call: Call<String>, response: Response<String>)  {
//                    idCheck = "${response.body()}"
//                    Log.d("ID Check return", "return ID $idCheck")
//                    isCheck = idCheck == "사용가능"
//                    Log.d("isCheck",isCheck.toString())
//
//                }
//                override fun onFailure(call: Call<String>, t: Throwable) {
//                    Log.d("ID check fail", t.message.toString())
//                    Log.d("ID check fail", "fail")
//                    isCheck = false
//                }
//
//            })
            val idCeckResult  = FeedRepository().idCheck(signUpId.text.toString())
            if (idCeckResult.value == "사용가능"){
                isCheck = true
            }
        }

        signUpAccessBtn.setOnClickListener {
            if (signUpId.text.isEmpty() || signUpPw.text.isEmpty() || signUpAccessBtn.text.isEmpty()){
                Toast.makeText(this,"값을 모두 입력해주세요",Toast.LENGTH_SHORT).show()
            }else if(!isCheck){
                Toast.makeText(this,"중복확인 X", android.widget.Toast.LENGTH_SHORT).show()
            }
            else{
                val userId : String = signUpId.text.toString()
                val userPw : String = signUpPw.text.toString()
                val userName : String = signUpName.text.toString()
                val userInfo = JoinMember(memberId = userId, memberName = userName, memberPwd = userPw)


                Log.d("log",userInfo.toString())
//
//                api.postUser(userInfo).enqueue(object : Callback<String>{
//                    override fun onResponse(
//                        call: Call<String>,
//                        response: Response<String>
//                    ) {
//                        if (!response.isSuccessful){
//                            Log.d("log", "Code ${response.code()}")
//                            Log.d("log", response.body().toString())
//
//                        }else{
//
//                            Log.d("log", response.toString())
//                            Log.d("log", response.body().toString())
//                            if(response.body().toString().isNotEmpty())
//                                Toast.makeText(applicationContext, response.body().toString(), Toast.LENGTH_SHORT).show()
//                        }
//                        finish()
//                    }
//                    override fun onFailure(call: Call<String>, t: Throwable) {
//                        Log.d("log", t.message.toString())
//                        Log.d("log","fail")
//                    }
//                })
            }
        }

    }
}