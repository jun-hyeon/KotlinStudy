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

data class LoginRequest(
    @SerializedName("memberId")
    val memberId : String,

    @SerializedName("memberPwd")
    val memberPwd : String
)


data class LoginInfo(
    @SerializedName("memberId")
    val memberId : String,

    @SerializedName("state")
    val memberState : String,

    @SerializedName("memberName")
    val memberName  : String
    )


data class FeedQueryItem(
    val feedContent: String?,
    val feedDate: String,
    val feedNumber: Long?,
    val ffList: List<Ff>?,
    val memberId: String
)

data class Ff(
    val feedCode: Long,
    val feedFileCode: Long,
    val feedFileImg: String,
    val feedFileSaveimg: String
)



