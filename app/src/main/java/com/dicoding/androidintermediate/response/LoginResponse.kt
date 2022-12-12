package com.dicoding.androidintermediate.response

import com.dicoding.androidintermediate.request.LoginResult
import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("error")
    val error: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("loginResult")
    val loginResult: LoginResult?,
)