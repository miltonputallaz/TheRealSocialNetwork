package com.sanicorporation.therealsocialnetwork.di.component

import android.app.Application
import com.sanicorporation.therealsocialnetwork.CustomApplication
import com.sanicorporation.therealsocialnetwork.di.module.PostNetworkingModule
import com.sanicorporation.therealsocialnetwork.di.module.SubComponentsModule
import com.sanicorporation.therealsocialnetwork.repository.PostRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(PostNetworkingModule::class, SubComponentsModule::class))
@Singleton
interface ApplicationComponent {

    fun providePersonRepository(): PostRepository


    fun inject(application: Application)

    fun mainComponent(): MainActivityComponent.Builder

    fun addPostComponent(): AddPostActivityComponent.Builder


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: CustomApplication):Builder

        fun build(): ApplicationComponent
    }

}