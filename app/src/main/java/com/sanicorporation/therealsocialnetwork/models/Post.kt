package com.sanicorporation.therealsocialnetwork.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Post(
    val title: String,
    val description: String,
    @SerializedName("likedcount")
    val likeCount: Int,
    @SerializedName("imageurl")
    val imageUrl: String?,
    @SerializedName("id")
    val postId: Long?,
    @SerializedName("favourite")
    val isFavourite: Boolean
):Serializable{
    constructor(
        title: String,
        description: String,
        likeCount: Int,
        imageUrl: String?,
        postId: Long?
    ) : this(title, description, likeCount,imageUrl,postId,false)
}
