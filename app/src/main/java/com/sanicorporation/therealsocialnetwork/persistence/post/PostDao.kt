package com.sanicorporation.therealsocialnetwork.persistence.post

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.sanicorporation.therealsocialnetwork.models.Post

@Dao
interface PostDao {
    @Insert(onConflict = REPLACE)
    suspend fun save(post: Post)

    @Insert(onConflict = REPLACE)
    suspend fun saveAll(posts: List<Post>)

    @Query("SELECT * FROM post")
    fun getLastPost(): LiveData<List<Post>>
}