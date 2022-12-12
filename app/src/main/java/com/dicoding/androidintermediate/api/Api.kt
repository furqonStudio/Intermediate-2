package com.dicoding.androidintermediate.api

import com.dicoding.androidintermediate.request.LoginRequest
import com.dicoding.androidintermediate.response.LoginResponse
import com.dicoding.androidintermediate.request.RegisterRequest
import com.dicoding.androidintermediate.response.RegisterResponse
import com.dicoding.androidintermediate.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @POST("register")
    fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("login")
    fun loginUser(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("stories")
    fun getStories(
        @Header("Authorization") token: String,
        @Query("location") location: Int = 0,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
    ): StoryResponse

    @Multipart
    @POST("stories")
    fun addStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody? = null,
        @Part("lon") long: RequestBody? = null,
    ): RegisterResponse
}