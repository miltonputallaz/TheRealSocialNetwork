package com.sanicorporation.therealsocialnetwork.activities.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sanicorporation.therealsocialnetwork.R
import com.sanicorporation.therealsocialnetwork.activities.add_post.AddPostActivity
import com.sanicorporation.therealsocialnetwork.activities.login.LoginActivity
import com.sanicorporation.therealsocialnetwork.databinding.ActivityMainBinding
import com.sanicorporation.therealsocialnetwork.models.Post
import com.sanicorporation.therealsocialnetwork.utils.Keys
import com.sanicorporation.therealsocialnetwork.utils.Preferences


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var viewModel: MainViewModel = MainViewModel()

    private val likedHandler: (postId: String, liked: Boolean) -> Unit = { postId, liked ->
        val uid = Preferences.INSTANCE.getString(this, Keys.UID.toString())
        viewModel.performLike(uid, postId, liked)

    }

    private val getLikeHandler: (postId: String, (liked: Boolean) -> Unit) -> Unit = { postId, setLikeHandler ->
        val uid = Preferences.INSTANCE.getString(this, Keys.UID.toString())
       viewModel.performVerifyPostLiked(postId, uid, setLikeHandler)
    }

    private val selectedHandler: (post: Post) -> Unit = {
        Log.d("","")
    }

    private val getImageHandler: (imageUri: String, imageView: ImageView) -> Unit = {uri, view ->
        Glide.with(this).load(uri).into(view)
    }

    private val logoutHandler: () -> Unit = {
        Preferences.INSTANCE.clear(this)
        goToLogin()
    }

    private val postsSuccessfulHandler: (ArrayList<Post>) -> Unit = {
        postAdapter.setPosts(it)
        binding.refresh.isRefreshing = false
    }

    private var postAdapter: PostAdapter = PostAdapter(ArrayList(), likedHandler, selectedHandler, getLikeHandler, getImageHandler)





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        setUpToolbar()
        setUpBinding()
        setUpRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        getLastPosts()
    }

    private fun setUpToolbar(){
        setSupportActionBar(binding.toolbar)
    }

    private fun setUpBinding(){
        binding.viewmodel = viewModel
        binding.handler = this
        binding.lifecycleOwner = this
    }

    private fun setUpRecyclerView(){
        val viewManager = LinearLayoutManager(this)
        binding.postsRecyclerView.apply {
            layoutManager = viewManager
            adapter = postAdapter
        }
        binding.refresh.setOnRefreshListener{
            getLastPosts()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }


    private fun getLastPosts(){
        viewModel.performGetLastPosts(postsSuccessfulHandler)
    }

    fun goToAddPost(){
        val intent = Intent(this, AddPostActivity::class.java)
        startActivity(intent)
    }

    private fun goToLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_logout -> {
            viewModel.performLogOut(logoutHandler)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

}
