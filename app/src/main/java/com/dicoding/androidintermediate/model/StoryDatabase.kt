package com.dicoding.androidintermediate.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.androidintermediate.response.Story

@Database(
    entities = [Story::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}