package com.sanicorporation.therealsocialnetwork.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.sanicorporation.therealsocialnetwork.models.CustomResponseBody
import com.sanicorporation.therealsocialnetwork.models.Post
import com.sanicorporation.therealsocialnetwork.models.PostId
import com.sanicorporation.therealsocialnetwork.network.PostService
import com.sanicorporation.therealsocialnetwork.persistence.post.PostDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class PostRepository @Inject constructor(
    private val remoteSource: PostService,
    private val localSource: PostDao
) {

    fun getLastPosts(offset: Int, success: () -> Unit, error: (error: String) -> Unit): LiveData<List<Post>>{
        refreshPosts(offset,success, error)
        return localSource.getLastPost()
    }

    private fun refreshPosts(offset: Int, success: () -> Unit, error: (error: String) -> Unit){
        remoteSource.getAllPost(offset).enqueue(object : Callback<List<Post>>{
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                error("Error")
            }

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful){
                    success()
                    CoroutineScope(IO).launch {
                        localSource.saveAll(response.body()!!)
                    }
                } else {
                    error("Error")
                }

            }

        })


    }

    fun addPost(post: Post, success: () -> Unit, error: (error: String) -> Unit){
        remoteSource.addPost(post).enqueue(object : Callback<CustomResponseBody>{
            override fun onFailure(call: Call<CustomResponseBody>, t: Throwable) {
                error("Error")
            }

            override fun onResponse(call: Call<CustomResponseBody>, response: Response<CustomResponseBody>) {
                if (response.isSuccessful){
                    success()
                } else {
                    error("Error")
                }
            }

        })
    }

    fun likePost(postId: PostId){
        remoteSource.addToFavourite(postId).enqueue(object : Callback<CustomResponseBody>{
            override fun onFailure(call: Call<CustomResponseBody>, t: Throwable) {
                Log.d("","")
            }

            override fun onResponse(call: Call<CustomResponseBody>, response: Response<CustomResponseBody>) {
                Log.d("","")
            }

        })
    }

    fun unlikePost(postId: PostId){
        remoteSource.removeFromFavourites(postId).enqueue(object : Callback<CustomResponseBody>{
            override fun onFailure(call: Call<CustomResponseBody>, t: Throwable) {
                Log.d("","")
            }

            override fun onResponse(call: Call<CustomResponseBody>, response: Response<CustomResponseBody>) {
                Log.d("","" )
            }

        })
    }

}