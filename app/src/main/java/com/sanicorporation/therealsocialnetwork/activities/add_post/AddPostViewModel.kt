package com.sanicorporation.therealsocialnetwork.activities.add_post

import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.FirebaseStorage
import com.sanicorporation.therealsocialnetwork.CustomApplication
import com.sanicorporation.therealsocialnetwork.models.Post
import com.sanicorporation.therealsocialnetwork.repository.PostRepository
import com.sanicorporation.therealsocialnetwork.utils.Keys


import java.io.File
import javax.inject.Inject

@BindingAdapter("bind:imageBitmap")
fun loadImage(iv: ImageView, bitmap: Bitmap?) {
    bitmap.apply {
        iv.setImageBitmap(bitmap)
    }
}

@BindingAdapter("bind:visibiltyImage")
fun imageVisibility(iv: ImageView, bitmap: Bitmap?) {
    bitmap?.apply {
        iv.visibility = View.VISIBLE
        return
    }
    iv.visibility = View.GONE
}



class AddPostViewModel : ViewModel() {

    var title: MutableLiveData<String> = MutableLiveData()
    var description: MutableLiveData<String> = MutableLiveData()
    var hasCamera: MutableLiveData<Boolean> = MutableLiveData()
    var showLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var bitmap: MutableLiveData<Bitmap> = MutableLiveData()

    lateinit var photoPath: String

    @Inject
    lateinit var postRepository: PostRepository

    init {
        CustomApplication
            .component
            .addPostComponent()
            .build()
            .inject(this)
        bitmap.value = null
    }

    fun setCameraState(isEnabled: Boolean){
        hasCamera.value = isEnabled
    }




    fun performAddPost(success: () -> Unit, error: () -> Unit){
        if (titleIsValid(title.value) && descriptionIsValid(description.value)){
            showLoading.value = true
            val imageHandler: (String?) -> Unit = {
                it?.also {
                    uploadPost(success, error, it)
                }
            }

            if (::photoPath.isInitialized ){
                loadImage(imageHandler)
            } else {
                uploadPost(success,error,null)
            }

        }
    }

    private fun uploadPost(success: () -> Unit, error: () -> Unit, url: String?) {
        val post = Post(title.value!!, description.value!!,0,url,null)
        showLoading(true)
        val success: () -> Unit = {
            showLoading(false)
            success()
        }
        val error: (error: String) -> Unit = {
            showLoading(false)
            error()
        }
        postRepository.addPost(post, success, error)


    }

    private fun loadImage(imageHandler: (String?) -> Unit) {
        val file: Uri = Uri.fromFile(File(photoPath))

        val imagesRef  = FirebaseStorage.getInstance().reference.child(Keys.IMAGES.toString()+"/"+file.lastPathSegment)
        val uploadTask = imagesRef.putFile(file)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imagesRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                imageHandler(task.result.toString())
            } else {
                imageHandler(null)
            }
        }


    }



    private fun titleIsValid(title : String?) : Boolean {
        return title != null && title.length >= 6
    }

    private fun descriptionIsValid(description : String?) : Boolean {
        return description != null && description.length >= 6
    }


    fun setImagePath(localPhotoPath: String) {
        this.photoPath  = localPhotoPath
    }

    fun setImageBitmap(bitmap: Bitmap) {
        this.bitmap.value = bitmap
    }

    private fun showLoading(show: Boolean){
        showLoading.value = show
    }

}