package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val user = Login("qwer","1234")
        val idText = binding.idEditText
        val pwText = binding.pwEditText

        binding.loginBtn.setOnClickListener {
            Log.d("LOGIN", "$idText, $pwText")

            if (user.isLogin("${idText.text}", "${pwText.text}")){
                Toast.makeText(this,"로그인 성공!!!",Toast.LENGTH_SHORT).show()

                val intent = Intent(this,SecondActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this,"로그인 실패!!!",Toast.LENGTH_SHORT).show()
            }
        }

    }
}






