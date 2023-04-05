package com.example.msololife.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.msololife.MainActivity
import com.example.msololife.R
import com.example.msololife.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {

    private lateinit var auth :FirebaseAuth
    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_join)

        auth = Firebase.auth

        binding.joinBtn.setOnClickListener {

            var isGoToJoin = true

            val email = binding.emailArea.text.toString()
            val password1 = binding.passwordArea1.text.toString()
            val password2 = binding.passwordArea2.text.toString()

            //이메일 빈칸확인
            if(email.isEmpty()){
                Toast.makeText(this,"이메일을 입력해주세요",Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }
            //password1 빈칸확인
            if (password1.isEmpty()){
                Toast.makeText(this,"password1를 입력해주세요",Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }
            //password2 빈칸확인
            if (password2.isEmpty()){
                Toast.makeText(this,"password2를 입력해주세요",Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }
            //비밀번호와 비밀번호확인 같은지 확인
            if(password1 != password2){
                Toast.makeText(this,"password를 똑같이 입력해주세요",Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }

            if (password1.length < 6){
                Toast.makeText(this,"비밀번호를 6자리 이상으로 입력해주세요",Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }
            if (isGoToJoin){

                auth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(this){
                        if(it.isSuccessful){
                            Toast.makeText(this,"성공",Toast.LENGTH_SHORT).show()

                            val intent = Intent(this,MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)

                        }else{
                            Toast.makeText(this,"실패",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }



    }
}