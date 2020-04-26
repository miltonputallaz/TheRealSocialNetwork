package com.sanicorporation.therealsocialnetwork.network

import com.sanicorporation.therealsocialnetwork.models.CustomResponseBody
import com.sanicorporation.therealsocialnetwork.models.Post
import com.sanicorporation.therealsocialnetwork.models.PostId
import retrofit2.Call

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface  PostService {

    @POST("post/add")
    fun addPost(@Body post: Post): Call<CustomResponseBody>

    @GET("post/all")
    fun getAllPost(): Call<ArrayList<Post>>

    @POST("post/addtofavourite")
    fun addToFavourite(@Body postId: PostId): Call<CustomResponseBody>

    @POST("post/removefromfavourites")
    fun removeFromFavourites(@Body postId: PostId): Call<CustomResponseBody>
}