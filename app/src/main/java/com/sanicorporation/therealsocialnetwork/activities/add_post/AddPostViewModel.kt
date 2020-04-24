package com.sanicorporation.therealsocialnetwork.activities.add_post

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sanicorporation.therealsocialnetwork.models.Post
import com.sanicorporation.therealsocialnetwork.network.BaseService
import com.sanicorporation.therealsocialnetwork.network.PostService
import com.sanicorporation.therealsocialnetwork.utils.Keys
import com.squareup.okhttp.ResponseBody
import retrofit2.Call
import retrofit2.Callback

import java.io.File

@BindingAdapter("bind:imageBitmap")
fun loadImage(iv: ImageView, bitmap: Bitmap?) {
    bitmap.also {
        iv.setImageBitmap(bitmap)
    }
}

class AddPostViewModel : ViewModel() {

    var title: MutableLiveData<String> = MutableLiveData()
    var description: MutableLiveData<String> = MutableLiveData()
    var hasCamera: MutableLiveData<Boolean> = MutableLiveData()
    var showLoading: MutableLiveData<Boolean> = MutableLiveData()
    var bitmap: MutableLiveData<Bitmap> = MutableLiveData()

    private val firestore = FirebaseFirestore.getInstance()
    lateinit var storage: FirebaseStorage
    lateinit var photoPath: String

    init {
        showLoading.value = false
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
        val postService = BaseService.retrofit.create(PostService::class.java)
        postService.addPost(post).enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                showLoading.value = false
                success()
            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: retrofit2.Response<ResponseBody>
            ) {
                showLoading.value = false
                error()
            }

        })

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

}