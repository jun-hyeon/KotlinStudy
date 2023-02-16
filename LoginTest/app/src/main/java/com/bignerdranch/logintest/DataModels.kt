package com.bignerdranch.logintest

import com.google.gson.annotations.SerializedName

data class JoinMember(
    @SerializedName("memberId")
    val memberId: String,

    @SerializedName("memberName")
    val memberName: String,

    @SerializedName("memberPwd")
    val memberPwd: String
)



data class PostResponse(
    val postResponse : String
        )




