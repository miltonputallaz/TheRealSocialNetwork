package com.sanicorporation.therealsocialnetwork.repository

import androidx.lifecycle.LiveData
import com.sanicorporation.therealsocialnetwork.models.Post

interface PostRepositoryInterface {

    suspend fun getLastPosts(offset: Int, success: () -> Unit, error: (error: String) -> Unit): LiveData<List<Post>>

    suspend fun refreshPosts(offset: Int, success: () -> Unit, error: (error: String) -> Unit)
}