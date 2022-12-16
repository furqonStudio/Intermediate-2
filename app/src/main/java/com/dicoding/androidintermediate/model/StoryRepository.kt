package com.dicoding.androidintermediate.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.dicoding.androidintermediate.api.Api
import com.dicoding.androidintermediate.api.ApiConfig
import com.dicoding.androidintermediate.request.LoginRequest
import com.dicoding.androidintermediate.request.LoginResult
import com.dicoding.androidintermediate.request.RegisterRequest
import com.dicoding.androidintermediate.request.UserSession
import com.dicoding.androidintermediate.response.LoginResponse
import com.dicoding.androidintermediate.response.RegisterResponse
import com.dicoding.androidintermediate.response.Story
import com.dicoding.androidintermediate.response.StoryResponse
import com.dicoding.androidintermediate.util.RETROFIT_TAG
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class StoryRepository @Inject constructor(
    private val storyDatabase: StoryDatabase,
    private val apiService: Api
    ) {

    private val _userLogin = MutableLiveData<LoginResult>()
    val userLogin: LiveData<LoginResult> = _userLogin

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    private val _listStory = MutableLiveData<List<Story>>()
    val listStory: LiveData<List<Story>> = _listStory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getStories(token: String): LiveData<PagingData<Story>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(
                storyDatabase,
                apiService,
                "Bearer $token"
            ),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStories()
            }
        ).liveData
    }

    fun loginUser(email: String, password: String) {
        _isLoading.value = true
        apiService.loginUser(LoginRequest(email, password))
        .enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _toastMessage.value = response.body()?.message!!
                    _userLogin.value = response.body()?.loginResult!!
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

    fun registerUser(name: String, email: String,password: String) {
        _isLoading.value = true
        ApiConfig.getApiService().register(RegisterRequest(name, email, password))
            (object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        Log.d(RETROFIT_TAG, response.body()?.message.toString())
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.d(RETROFIT_TAG, t.message.toString())
                }

            })

    }

    fun getAllStoriesWithLocation(token: String): LiveData<Result<StoryResponse>> = liveData {
            try {
                val bearerToken = "Bearer $token"
                val response = apiService.getStories(bearerToken,location = 1, page = 5, size = 30)
                emit(Result.success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.failure(e))
            }
    }

    suspend fun addStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody? = null,
        lon: RequestBody? = null
    ): LiveData<Result<RegisterResponse>> = liveData {
        try {
            val bearerToken = "Bearer $token"
            val response = apiService.addStory(bearerToken, file, description, lat, lon)
            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }



}