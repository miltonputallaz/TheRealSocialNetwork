package com.sanicorporation.therealsocialnetwork.network

import com.sanicorporation.therealsocialnetwork.models.Post
import com.sanicorporation.therealsocialnetwork.models.PostId
import com.squareup.okhttp.ResponseBody
import retrofit2.Call

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface  PostService {

    @POST("post/add")
    fun addPost(@Body post: Post): Call<ResponseBody>

    @GET("post/all")
    fun getAllPost(): Call<ArrayList<Post>>

    @POST("post/addtofavourite")
    fun addToFavourite(@Body postId: PostId): Call<ResponseBody>

    @POST("post/removefromfavourites")
    fun removeFromFavourites(@Body postId: PostId): Call<ResponseBody>
}