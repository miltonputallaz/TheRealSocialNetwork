package com.sanicorporation.therealsocialnetwork.di.component

import com.sanicorporation.therealsocialnetwork.activities.main.MainViewModel
import dagger.Subcomponent

@Subcomponent
interface MainActivityComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): MainActivityComponent
    }


    fun inject(mainViewModel: MainViewModel)
}