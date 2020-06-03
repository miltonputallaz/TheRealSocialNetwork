package com.sanicorporation.therealsocialnetwork.persistence.post

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sanicorporation.therealsocialnetwork.models.Post

@Database(entities = [Post::class], version = 1)
abstract class RSNDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}
