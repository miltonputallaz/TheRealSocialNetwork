package com.sanicorporation.therealsocialnetwork.utils

enum class Keys(var stringify: String) {
    UID("UID"),

    //FIREBASEKEYS
    POSTS("posts"),
    LIKES("likes"),
    IMAGES("images");


    override fun toString(): String {
        return stringify // working!
    }

}
