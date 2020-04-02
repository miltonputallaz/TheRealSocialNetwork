package com.sanicorporation.therealsocialnetwork.activities.add_post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class AddPostViewModel : ViewModel() {
    var title: MutableLiveData<String> = MutableLiveData()
    var description: MutableLiveData<String> = MutableLiveData()
    private val firestore = FirebaseFirestore.getInstance()



    fun performAddPost(collection: String, success: () -> Unit, error: () -> Unit){
        if (titleIsValid(title.value) && descriptionIsValid(description.value)){
            val post = hashMapOf(
                "title" to title.value!!,
                "description" to description.value!!
            )

            firestore
                .collection(collection)
                .add(post)
                .addOnSuccessListener {
                    success()
                }
                .addOnFailureListener{ failure ->
                    error()
                }

        }
    }


    private fun titleIsValid(title : String?) : Boolean {
        return title != null && title.length >= 6
    }

    private fun descriptionIsValid(description : String?) : Boolean {
        return description != null && description.length >= 6
    }

}