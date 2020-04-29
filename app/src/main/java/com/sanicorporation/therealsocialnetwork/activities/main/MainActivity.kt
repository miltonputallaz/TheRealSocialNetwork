package com.sanicorporation.therealsocialnetwork.activities.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import androidx.core.util.Pair as UtilPair
import com.sanicorporation.therealsocialnetwork.R
import com.sanicorporation.therealsocialnetwork.activities.BaseActivity
import com.sanicorporation.therealsocialnetwork.activities.add_post.AddPostActivity
import com.sanicorporation.therealsocialnetwork.activities.login.LoginActivity
import com.sanicorporation.therealsocialnetwork.activities.post_detail.PostDetailActivity
import com.sanicorporation.therealsocialnetwork.databinding.ActivityMainBinding
import com.sanicorporation.therealsocialnetwork.models.Post
import com.sanicorporation.therealsocialnetwork.utils.Keys
import com.sanicorporation.therealsocialnetwork.utils.Preferences
import kotlinx.android.synthetic.main.post_item.view.*



class MainActivity : BaseActivity() {

    private val ADD_ITEM_REQUEST = 200

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    private val likedHandler: (postId: Long, liked: Boolean) -> Unit = { postId, liked ->
        viewModel.performLike( postId, liked)
    }



    private val selectedHandler: (view: View, post: Post) -> Unit = {view, post ->
        goToDetail(view, post)
    }

    private val getImageHandler: (imageUri: String, imageView: ImageView) -> Unit = {uri, view ->
        Glide.with(this).load(uri).into(view)
    }

    private val logoutHandler: () -> Unit = {
        Preferences.INSTANCE.clear(this)
        goToLogin()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setUpToolbar()
        setUpBinding()
        setUpRecyclerView()
    }



    private fun setUpToolbar(){
        setSupportActionBar(binding.toolbar)
    }

    private fun setUpBinding(){
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewmodel = viewModel
        binding.handler = this
    }

    private fun setUpRecyclerView(){
        val viewManager = LinearLayoutManager(this)
        binding.postsRecyclerView.apply {
            layoutManager = viewManager
            adapter = PostAdapter(ArrayList(), likedHandler, selectedHandler, getImageHandler)
        }
        binding.refresh.setOnRefreshListener{
            viewModel.isRefreshing(true)
            viewModel.resetOffset()
            getLastPosts()
        }

        binding.postsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                recyclerView.layoutManager
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }


    private fun getLastPosts(){
        viewModel.performGetLastPosts()
    }

    fun goToAddPost(){
        val intent = Intent(this, AddPostActivity::class.java)
        startActivityForResult(intent,ADD_ITEM_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_ITEM_REQUEST && resultCode == Activity.RESULT_OK)
            getLastPosts()
    }

    private fun goToLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToDetail(view: View, post: Post){
        val intent = Intent(this, PostDetailActivity::class.java)
        intent.putExtra(PostDetailActivity.SELECTED_POST, post)
        val p1 = UtilPair(view.post_image as View, "post_image")
        val p2 = UtilPair(view.post_title as View, "post_title")
        val p3 = UtilPair(view.post_message as View, "post_message")
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,p1,p2,p3)
        startActivity(intent, options.toBundle())
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_logout -> {
            viewModel.performLogOut(logoutHandler)
            true
        }

        R.id.action_change_theme -> {
            changeTheme()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun changeTheme() {
        val theme = Preferences.INSTANCE.getString(this,Keys.THEME_ID.toString())
        if(theme == Keys.THEME_LIGHT.toString())
            Preferences.INSTANCE.addString(this, Keys.THEME_ID.toString(), Keys.THEME_DARK.toString())
        else
            Preferences.INSTANCE.addString(this, Keys.THEME_ID.toString(),Keys.THEME_LIGHT.toString())
        recreate()
    }

}
