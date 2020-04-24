package com.sanicorporation.therealsocialnetwork.activities.main


import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sanicorporation.therealsocialnetwork.models.Post
import com.sanicorporation.therealsocialnetwork.models.PostId
import com.sanicorporation.therealsocialnetwork.network.BaseService
import com.sanicorporation.therealsocialnetwork.network.PostService
import com.squareup.okhttp.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()



    fun performLogOut(logoutHandler: () -> Unit) {
        auth.signOut()
        logoutHandler()
    }

    fun performGetLastPosts(getPostsHandler: (ArrayList<Post>) -> Unit){

        val postService = BaseService.retrofit.create(PostService::class.java)
        postService.getAllPost().enqueue(object : Callback<ArrayList<Post>> {
            override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
                Log.d("","")
            }

            override fun onResponse(
                call: Call<ArrayList<Post>>,
                response: retrofit2.Response<ArrayList<Post>>
            ) {
                response.body()?.let {
                    getPostsHandler(it)
                }
            }

        })


    }

    fun performLike( postId: Long, liked: Boolean) {
        if (liked){
            likePost(postId)
        } else {
            unlikePost(postId)
        }

    }

    private fun unlikePost(postId: Long) {

        val postService = BaseService.retrofit.create(PostService::class.java)
        postService.removeFromFavourites(PostId(postId)).enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("","")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("","" )
            }

        })
    }

    private fun likePost(postId: Long) {
        val postService = BaseService.retrofit.create(PostService::class.java)
        postService.addToFavourite(PostId(postId)).enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("","")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("","")
            }

        })
    }



}