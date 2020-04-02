package com.sanicorporation.therealsocialnetwork.activities.main


import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sanicorporation.therealsocialnetwork.models.Post


class MainViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()



    fun performLogOut(logoutHandler: () -> Unit) {
        auth.signOut()
        logoutHandler()
    }

    fun performGetLastPosts(collection: String,getPostsHandler: (ArrayList<Post>) -> Unit){
        firestore.collection(collection)
            .get()
            .addOnSuccessListener { result ->
                var collection: ArrayList<Post> = ArrayList()
                for (document in result) {
                    var post: Post = Post(document.data["title"] as String, document.data["description"] as String)
                    collection.add(post)
                }
                getPostsHandler(collection)
            }
            .addOnFailureListener { exception ->
                Log.d("","")
            }

    }

}