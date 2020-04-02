package com.sanicorporation.therealsocialnetwork.activities.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanicorporation.therealsocialnetwork.R
import com.sanicorporation.therealsocialnetwork.activities.add_post.AddPostActivity
import com.sanicorporation.therealsocialnetwork.activities.login.LoginActivity
import com.sanicorporation.therealsocialnetwork.databinding.ActivityMainBinding
import com.sanicorporation.therealsocialnetwork.models.Post


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var viewModel: MainViewModel = MainViewModel()
    private var postAdapter: PostAdapter = PostAdapter(ArrayList())

    private val logoutHandler: () -> Unit = {
        goToLogin()
    }

    private val postsSuccessfulHandler: (ArrayList<Post>) -> Unit = {
        postAdapter.setPosts(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        setUpToolbar()
        setUpBinding()
        setUpRecyclerView()
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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }


    private fun getLastPosts(){
        val collection = getString(R.string.collection_post_name)
        viewModel.performGetLastPosts(collection,postsSuccessfulHandler)
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
