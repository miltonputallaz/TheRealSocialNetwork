package com.sanicorporation.therealsocialnetwork.activities.add_post

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sanicorporation.therealsocialnetwork.utils.Keys
import java.io.File


class AddPostViewModel : ViewModel() {
    var title: MutableLiveData<String> = MutableLiveData()
    var description: MutableLiveData<String> = MutableLiveData()
    var hasCamera: MutableLiveData<Boolean> = MutableLiveData()
    var sendingData: MutableLiveData<Boolean> = MutableLiveData()

    private val firestore = FirebaseFirestore.getInstance()
    lateinit var storage: FirebaseStorage
    lateinit var photoPath: String

    init {
        sendingData.value = false
    }

    fun setCameraState(isEnabled: Boolean){
        hasCamera.value = isEnabled
    }




    fun performAddPost(success: () -> Unit, error: () -> Unit){
        if (titleIsValid(title.value) && descriptionIsValid(description.value)){
            sendingData.value = true
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
        val post = hashMapOf(
            "title" to title.value!!,
            "description" to description.value!!,
            "imageUrl" to url
        )

        firestore
            .collection(Keys.POSTS.toString())
            .add(post)
            .addOnSuccessListener {
                sendingData.value = false
                success()
            }
            .addOnFailureListener{ failure ->
                sendingData.value = false
                error()
            }
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

}