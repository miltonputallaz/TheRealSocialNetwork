package com.sanicorporation.therealsocialnetwork.network

import com.sanicorporation.therealsocialnetwork.models.CustomResponseBody
import com.sanicorporation.therealsocialnetwork.models.Post
import com.sanicorporation.therealsocialnetwork.models.PostId
import retrofit2.Call

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface  PostService {

    @POST("post/add")
    fun addPost(@Body post: Post): Call<CustomResponseBody>

    @GET("post/all")
    fun getAllPost(@Query("offset") offset: Int): Call<List<Post>>

    @POST("favourite/add")
    fun addToFavourite(@Body postId: PostId): Call<CustomResponseBody>

    @POST("favourite/remove")
    fun removeFromFavourites(@Body postId: PostId): Call<CustomResponseBody>
}