package com.dicoding.androidintermediate.request

data class UserSession(
    val name: String,
    val token: String,
    val userId: String,
    val isLogin: Boolean
)