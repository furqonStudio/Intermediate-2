package com.dicoding.androidintermediate.model

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.dicoding.androidintermediate.api.Api
import com.dicoding.androidintermediate.response.Story

class StoryRepository (
    private val storyDatabase: StoryDatabase,
    private val apiService: Api
    ) {

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

}