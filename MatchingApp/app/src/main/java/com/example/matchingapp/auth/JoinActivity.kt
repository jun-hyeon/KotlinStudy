package com.example.matchingapp.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.matchingapp.MainActivity
import com.example.matchingapp.R
import com.example.matchingapp.databinding.ActivityJoinBinding
import com.example.matchingapp.utils.FirebaseRef
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityJoinBinding.inflate(layoutInflater)
    }

    private lateinit var auth : FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        // 닉네임 성별 지역 나이 UID

        binding.joinBtn.setOnClickListener {
            val email = binding.emailArea.text.toString()
            val password = binding.pwdArea.text.toString()

            val nickname = binding.nicknameArea.text.toString()
            val gender = binding.genderArea.text.toString()
            val city = binding.cityArea.text.toString()
            val age = binding.ageArea.text.toString()


            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("JoinActivity", "createUserWithEmail:success")

                        val user = auth.currentUser
                        val uid = user?.uid.toString()

                        val userDataModel = UserDataModel(nickname, gender,city,age)

                        FirebaseRef.userInfoRef.child(uid).setValue(userDataModel)

//                        val intent = Intent(this, MainActivity::class.java)
//                        startActivity(intent)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("JoinActivity", "createUserWithEmail:failure", task.exception)

                    }
                }

        }

    }
}