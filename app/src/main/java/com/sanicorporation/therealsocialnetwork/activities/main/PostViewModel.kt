package com.sanicorporation.therealsocialnetwork.activities.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostViewModel(val likeHandler :(likedId: Long, liked: Boolean) -> Unit): ViewModel() {
    var isFavourite: MutableLiveData<Boolean> = MutableLiveData()
}