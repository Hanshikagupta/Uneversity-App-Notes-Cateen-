package com.example.newapplication.myapplication.model

import android.provider.ContactsContract.CommonDataKinds.Phone

data class UserModel(
    val name:String?=null,
    val email:String?=null,
    val password:String?=null,
    val branch: String?=null,
    val college: String?=null,
    val phone: String?=null,
    val tableno: String?=null,
)

