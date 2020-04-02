package com.sanicorporation.therealsocialnetwork.models

data class Post(val title: String, val message: String){
    constructor(title: String, message: String, imageUri: String, postId: Long) : this(title,message)
}