package com.sanicorporation.therealsocialnetwork.models

import com.google.firebase.firestore.QueryDocumentSnapshot

data class Post(val title: String, val message: String, val isFavourite: Boolean, val postId: String, val imageUri: String?){
    constructor(title: String, message: String, imageUri: String?, postId: String) : this(title,message, false, postId, imageUri)

    companion object {
        fun fromFirebase(document: QueryDocumentSnapshot): Post{
            val imageUri: String? = document.data["imageUrl"] as String?
            return Post(document.data["title"] as String, document.data["description"] as String, imageUri, document.id)
        }
    }
}
