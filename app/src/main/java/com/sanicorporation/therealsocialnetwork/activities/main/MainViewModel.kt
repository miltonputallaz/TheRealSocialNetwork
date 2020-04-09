package com.sanicorporation.therealsocialnetwork.activities.main


import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sanicorporation.therealsocialnetwork.models.Post
import com.sanicorporation.therealsocialnetwork.utils.Keys


class MainViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()



    fun performLogOut(logoutHandler: () -> Unit) {
        auth.signOut()
        logoutHandler()
    }

    fun performGetLastPosts(getPostsHandler: (ArrayList<Post>) -> Unit){
        firestore.collection(Keys.POSTS.toString())
            .get()
            .addOnSuccessListener { result ->
                var collection: ArrayList<Post> = ArrayList()
                for (document in result) {
                    val post = Post.fromFirebase(document)
                    collection.add(post)
                }
                getPostsHandler(collection)
            }
            .addOnFailureListener { exception ->
                Log.d("","")
            }

    }

    fun performLike(uid: String?, postId: String, liked: Boolean) {
        if (liked){
            val like = hashMapOf(
                "postId" to postId
            )
            firestore.collection(Keys.LIKES.toString()).document(uid.toString()).collection(Keys.POSTS.toString()).document(postId).set(like)
        } else {
            firestore.collection(Keys.LIKES.toString()).document(uid.toString()).collection(Keys.POSTS.toString()).document(postId).delete()
        }

    }

    fun performVerifyPostLiked(postId: String, uid: String?, likeHandler: (liked: Boolean) -> Unit) {
        val likesRef = firestore.collection(Keys.LIKES.toString()).document(uid.toString()).collection(Keys.POSTS.toString())
        likesRef.whereEqualTo("postId", postId).get() .addOnSuccessListener { document ->
            if (!document.documents.isEmpty()) {
                likeHandler(true)
            } else {
                likeHandler(false)
            }
        }
        .addOnFailureListener{
            likeHandler(false)
        }

    }


}