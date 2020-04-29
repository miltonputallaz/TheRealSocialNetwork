package com.sanicorporation.therealsocialnetwork.activities.main


import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.auth.FirebaseAuth
import com.sanicorporation.therealsocialnetwork.models.CustomResponseBody
import com.sanicorporation.therealsocialnetwork.models.Post
import com.sanicorporation.therealsocialnetwork.models.PostId
import com.sanicorporation.therealsocialnetwork.network.BaseService
import com.sanicorporation.therealsocialnetwork.network.PostService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@BindingAdapter("bind:adapter")
fun rvAdapter(rv: RecyclerView, posts: LiveData<ArrayList<Post>>) {
    posts.value?.apply {
        val adapter = rv.adapter as PostAdapter
        adapter.setPosts(this)
    }
}

@BindingAdapter("bind:refreshing")
fun refresherHandler(srLayout: SwipeRefreshLayout, isRefreshing: LiveData<Boolean>) {
    isRefreshing.apply {
        if (!this.value!!) srLayout.isRefreshing = this.value!!
    }
}


class MainViewModel : ViewModel() {

    private var isRefreshing: MutableLiveData<Boolean> = MutableLiveData()
    private val posts: MutableLiveData<ArrayList<Post>> by lazy {
        MutableLiveData<ArrayList<Post>>().also {
            performGetLastPosts()
        }
    }


    private val auth = FirebaseAuth.getInstance()
    private var page: Int = 0
    private val incrementCoefficient: Int = 10


    init {
        isRefreshing.value = false
    }

    fun isRefreshing():LiveData<Boolean>{
        return isRefreshing
    }

    fun isRefreshing(isRefreshing: Boolean){
        this.isRefreshing.value = isRefreshing
    }

    fun posts():LiveData<ArrayList<Post>>{
        return posts
    }

    fun posts(posts: ArrayList<Post>){
        this.posts.value = posts
    }




    fun performLogOut(logoutHandler: () -> Unit) {
        auth.signOut()
        logoutHandler()
    }

    fun performGetLastPosts(){
        val postService = BaseService.retrofit.create(PostService::class.java)
        postService.getAllPost(page*incrementCoefficient).enqueue(object : Callback<ArrayList<Post>> {
            override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
                Log.d("","")
            }

            override fun onResponse(
                call: Call<ArrayList<Post>>,
                response: retrofit2.Response<ArrayList<Post>>
            ) {
                response.body()?.let {
                    isRefreshing.value = false
                    if (page == 0)
                        posts.value = it
                    else
                        posts.value!!.addAll(it)

                    if (it.size == incrementCoefficient) incrementOffset()
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
        postService.removeFromFavourites(PostId(postId)).enqueue(object : Callback<CustomResponseBody>{
            override fun onFailure(call: Call<CustomResponseBody>, t: Throwable) {
                Log.d("","")
            }

            override fun onResponse(call: Call<CustomResponseBody>, response: Response<CustomResponseBody>) {
                Log.d("","" )
            }

        })
    }

    private fun likePost(postId: Long) {
        val postService = BaseService.retrofit.create(PostService::class.java)
        postService.addToFavourite(PostId(postId)).enqueue(object : Callback<CustomResponseBody>{
            override fun onFailure(call: Call<CustomResponseBody>, t: Throwable) {
                Log.d("","")
            }

            override fun onResponse(call: Call<CustomResponseBody>, response: Response<CustomResponseBody>) {
                Log.d("","")
            }

        })
    }

    fun resetOffset(){
        page = 0
    }

    private fun incrementOffset(){
        page += 1
    }



}