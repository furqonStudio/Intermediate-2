package com.dicoding.androidintermediate.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.androidintermediate.model.StoryRepository
import com.dicoding.androidintermediate.response.Story
import com.dicoding.androidintermediate.response.StoryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(private val storyRepository: StoryRepository) : ViewModel() {

    val listStory: LiveData<List<Story>> = storyRepository.listStory

    fun getAllStoryWithMaps(token: String) {
        storyRepository.getAllStoriesWithLocation("Bearer $token")
    }

    fun getAllStoriesWithLocation(token: String): LiveData<Result<StoryResponse>> =
        storyRepository.getAllStoriesWithLocation(token)
}