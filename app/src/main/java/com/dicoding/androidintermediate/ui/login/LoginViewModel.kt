package com.dicoding.androidintermediate.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.androidintermediate.api.ApiConfig
import com.dicoding.androidintermediate.request.LoginRequest
import com.dicoding.androidintermediate.response.LoginResponse
import com.dicoding.androidintermediate.request.LoginResult
import com.dicoding.androidintermediate.request.RegisterRequest
import com.dicoding.androidintermediate.response.RegisterResponse
import com.dicoding.androidintermediate.util.RETROFIT_TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val _userLogin = MutableLiveData<LoginResult?>()
    val userLogin: MutableLiveData<LoginResult?> = _userLogin

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: MutableLiveData<String?> = _toastMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loginUser(email: String, password: String) {
        _isLoading.value = true
        ApiConfig.getApiService().loginUser(LoginRequest(email, password))
        (object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _toastMessage.value = response.body()?.message
                    _userLogin.value = response.body()?.loginResult
                    Log.d(RETROFIT_TAG, response.body()?.message.toString())
                    Log.d(RETROFIT_TAG, response.body()?.loginResult?.token.toString())
                    Log.d(RETROFIT_TAG, response.body()?.loginResult?.name ?: "name")
                    Log.d(RETROFIT_TAG, response.body()?.loginResult?.userId ?: "userId")
                }
                if (!response.isSuccessful) {
                    _toastMessage.value = response.message()
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _toastMessage.value = t.message
                _isLoading.value = false
                Log.d(RETROFIT_TAG, t.message.toString())
            }

        })

    }

}