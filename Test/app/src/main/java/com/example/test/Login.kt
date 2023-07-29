package com.example.test

data class Login(
    val Id : String,
    val Pw : String,

){
    fun isLogin(typedId: String, typedPw: String): Boolean {
        return Id == typedId && Pw == typedPw
    }
}
