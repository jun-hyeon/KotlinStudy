package com.example.matchingapp.auth

import android.provider.ContactsContract.CommonDataKinds.Nickname

data class UserDataModel(
    val uid : String? = null,
    val nickname: String? = null,
    val age : String? = null,
    val gender : String? = null,
    val city : String? = null,
)
