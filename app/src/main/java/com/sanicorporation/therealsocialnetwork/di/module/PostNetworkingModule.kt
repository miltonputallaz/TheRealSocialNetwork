package com.sanicorporation.therealsocialnetwork.di.module

import com.sanicorporation.therealsocialnetwork.network.PostService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = arrayOf(ApplicationModule::class))
class PostNetworkingModule {

    @Provides
    @Singleton
    fun providePersonRemoteSource(retrofit: Retrofit): PostService{
        return retrofit.create(PostService::class.java)
    }
}