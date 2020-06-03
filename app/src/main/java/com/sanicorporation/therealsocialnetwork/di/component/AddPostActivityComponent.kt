package com.sanicorporation.therealsocialnetwork.di.component

import com.sanicorporation.therealsocialnetwork.activities.add_post.AddPostViewModel
import dagger.Subcomponent

@Subcomponent
interface AddPostActivityComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): AddPostActivityComponent
    }


    fun inject(mainViewModel: AddPostViewModel)
}