package com.dicoding.androidintermediate.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.androidintermediate.api.ApiConfig
import com.dicoding.androidintermediate.model.StoryRepository
import com.dicoding.androidintermediate.response.Story
import com.dicoding.androidintermediate.response.StoryResponse
import com.dicoding.androidintermediate.util.RETROFIT_TAG
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val storyRepository: StoryRepository
) : ViewModel(){

    private val _listStory = MutableLiveData<ArrayList<Story>>()
    val listStory: LiveData<ArrayList<Story>> = _listStory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading




    fun getAllStories(token: String): LiveData<PagingData<Story>> =
        storyRepository.getStories(token).cachedIn(viewModelScope)

//    fun getAllStories(token: String) {
//        _isLoading.value = true
//        ApiConfig.getApiService().getStories("Bearer $token", 1, null, null)
//            (object : Callback<StoryResponse> {
//                override fun onResponse(
//                    call: Call<StoryResponse>,
//                    response: Response<StoryResponse>
//                ) {
//                    _isLoading.value = false
//                    if (response.isSuccessful) {
//                        _listStory.postValue(response.body()?.listStory as ArrayList<Story>?)
//                        Log.d(RETROFIT_TAG, response.body()?.listStory.toString())
//                    }
//
//                }
//
//                override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
//                    _isLoading.value = false
//                    Log.d(RETROFIT_TAG, t.message.toString())
//                }
//
//            })
//    }
}