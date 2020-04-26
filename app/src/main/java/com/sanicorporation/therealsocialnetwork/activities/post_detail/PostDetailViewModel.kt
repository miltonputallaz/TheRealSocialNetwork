package com.sanicorporation.therealsocialnetwork.activities.post_detail

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide

@BindingAdapter("bind:imageBitmap")
fun loadImage(iv: ImageView, url: String?) {
    url?.also {
        Glide.with(iv.context).load(url).into(iv)
    }
}

class PostDetailViewModel: ViewModel(){
    var url: String?

    init {
        url = null
    }
}