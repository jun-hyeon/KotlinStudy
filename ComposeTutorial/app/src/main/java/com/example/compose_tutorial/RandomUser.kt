package com.example.compose_tutorial

data class RandomUser (
    val name : String = "개발하는 정대리",
    val description : String = "오늘도 빡코딩 하고 계신가요?",
    val profileImage: String = ""
)

object DummyDataProvider{
    val userList = List<RandomUser>(200){RandomUser()}
}