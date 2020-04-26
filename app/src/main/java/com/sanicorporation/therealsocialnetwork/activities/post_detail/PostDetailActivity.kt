package com.sanicorporation.therealsocialnetwork.activities.post_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.sanicorporation.therealsocialnetwork.R
import com.sanicorporation.therealsocialnetwork.databinding.ActivityPostDetailBinding
import com.sanicorporation.therealsocialnetwork.models.Post


class PostDetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityPostDetailBinding
    lateinit var post: Post
    private var viewModel: PostDetailViewModel = PostDetailViewModel()

    companion object {
        const val SELECTED_POST = "SELECTED_POST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_post_detail)
        post = intent.getSerializableExtra(SELECTED_POST) as Post
        setUpBinding()
        setUpToolbar()
        setUpData()
    }

    private fun setUpData() {
        binding.postTitle.text = post.title
        binding.postMessage.text = post.description
        post.imageUrl?.apply {
            viewModel.url = this
        }
    }

    private fun setUpToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        onBackPressed()
        return true
    }

    private fun setUpBinding(){
        binding.viewmodel = viewModel
        binding.handler = this
        binding.lifecycleOwner = this
    }
}
