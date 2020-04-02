package com.sanicorporation.therealsocialnetwork.activities.add_post

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.sanicorporation.therealsocialnetwork.R
import com.sanicorporation.therealsocialnetwork.databinding.ActivityAddPostBinding

class AddPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPostBinding

    private var addPostViewModel: AddPostViewModel = AddPostViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_post)
        setUpBinding()
        setUpToolbar()
    }

    private fun setUpBinding(){
        binding.viewmodel = addPostViewModel
        binding.handler = this
    }

    private fun setUpToolbar(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        onBackPressed()
        return true
    }

    private val sucessFullHandler: () -> Unit = {
        showSuccessfulAddedDialog()
    }

    private val errorHandler: () -> Unit ={

    }

    private val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        goToMain()
    }

    fun onClickShare(){
        val collection = getString(R.string.collection_post_name)
        addPostViewModel.performAddPost(collection, sucessFullHandler, errorHandler)
    }

    private fun goToMain(){
        onBackPressed()
    }

    private fun showSuccessfulAddedDialog(){
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle(R.string.addpost_successful_added_title)
            setMessage(R.string.addpost_successful_added_message)
            setPositiveButton(R.string.addpost_successful_added_button, DialogInterface.OnClickListener(function = positiveButtonClick))
            show()
        }
    }



}
