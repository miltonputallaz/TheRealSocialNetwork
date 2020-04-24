package com.sanicorporation.therealsocialnetwork.utils

enum class Keys(var stringify: String) {
    UID("UID"),
    THEME_ID("THEME_ID"),
    THEME_LIGHT("THEME_LIGHT"),
    THEME_DARK("THEME_DARK"),

    //FIREBASEKEYS
    POSTS("posts"),
    LIKES("likes"),
    IMAGES("images");


    override fun toString(): String {
        return stringify // working!
    }

}
