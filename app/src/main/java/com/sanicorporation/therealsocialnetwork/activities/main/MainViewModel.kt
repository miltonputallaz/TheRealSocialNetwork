package com.sanicorporation.therealsocialnetwork.activities.main


import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.auth.FirebaseAuth
import com.sanicorporation.therealsocialnetwork.CustomApplication
import com.sanicorporation.therealsocialnetwork.models.Post
import com.sanicorporation.therealsocialnetwork.models.PostId
import com.sanicorporation.therealsocialnetwork.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@BindingAdapter("bind:adapter")
fun rvAdapter(rv: RecyclerView, posts: LiveData<List<Post>>) {
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

    var isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    var swipeRefresh: MutableLiveData<Boolean> = MutableLiveData(false)
    val posts: MediatorLiveData<List<Post>> = liveData(context = viewModelScope.coroutineContext + Main) {
        showLoading(true)
        val posts = performGetLastPosts()
        emitSource(posts)
    } as MediatorLiveData<List<Post>>

    @Inject
    lateinit var postRepository: PostRepository

    private val auth = FirebaseAuth.getInstance()
    private var page: Int = 0
    private val incrementCoefficient: Int = 10


    init {
        CustomApplication
            .component
            .mainComponent()
            .build()
            .inject(this)
    }


    fun performLogOut(logoutHandler: () -> Unit) {
        auth.signOut()
        logoutHandler()
    }

    fun performGetLastPosts(): LiveData<List<Post>> = liveData(context = viewModelScope.coroutineContext + IO) {
        val success: () -> Unit = {
            showLoading(false)
            showSwipeLoading(false)
        }

        val error: (error: String) -> Unit = {
            showLoading(false)
            showSwipeLoading(false)
        }
        emitSource(postRepository.getLastPosts(page*incrementCoefficient, success, error))
    }



    fun refreshData(){
        posts.addSource(performGetLastPosts(), {})
    }




    fun performLike( postId: Long, liked: Boolean) {
        if (liked){
            likePost(postId)
        } else {
            unlikePost(postId)
        }
    }

    private fun unlikePost(postId: Long) {
        postRepository.unlikePost(PostId(postId))
    }

    private fun likePost(postId: Long) {
        postRepository.likePost(PostId(postId))
    }

    fun resetOffset(){
        page = 0
    }

    private fun incrementOffset(){
        page += 1
    }

    private fun showLoading(show: Boolean){
        isRefreshing.value = show
    }

    private fun showSwipeLoading(show: Boolean){
        swipeRefresh.value = show
    }



}