package com.ndiman.classmath.data.pref

data class UserModel(
    val username: String,
    val token: String,
    val isLogin: Boolean = false
)
