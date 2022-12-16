package com.dicoding.androidintermediate.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.androidintermediate.api.ApiConfig
import com.dicoding.androidintermediate.model.StoryRepository
import com.dicoding.androidintermediate.request.LoginRequest
import com.dicoding.androidintermediate.response.LoginResponse
import com.dicoding.androidintermediate.request.LoginResult
import com.dicoding.androidintermediate.request.RegisterRequest
import com.dicoding.androidintermediate.response.RegisterResponse
import com.dicoding.androidintermediate.util.RETROFIT_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val storyRepository: StoryRepository): ViewModel() {

    val userLogin: LiveData<LoginResult> = storyRepository.userLogin

    val toastMessage: LiveData<String> = storyRepository.toastMessage

    val isLoading: LiveData<Boolean> = storyRepository.isLoading

    fun loginUser(email: String, password: String) = storyRepository.loginUser(email, password)

}