package com.sanicorporation.therealsocialnetwork.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Post(
    val title: String,
    val description: String,
    @SerializedName("likedcount")
    var likeCount: Int,
    @SerializedName("imageurl")
    val imageUrl: String?,
    @PrimaryKey
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
