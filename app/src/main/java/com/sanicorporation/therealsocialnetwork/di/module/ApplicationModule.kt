package com.sanicorporation.therealsocialnetwork.di.module

import androidx.room.Room
import com.sanicorporation.therealsocialnetwork.CustomApplication
import com.sanicorporation.therealsocialnetwork.network.BaseInterceptor
import com.sanicorporation.therealsocialnetwork.persistence.post.PostDao
import com.sanicorporation.therealsocialnetwork.persistence.post.RSNDatabase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule() {
    private  val BASE_URL = "http://192.168.1.7:3001"

    @Provides
    @Singleton
    fun provideHTTPClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(BaseInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {

        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();
    }

    @Provides
    @Singleton
    fun provideDatabase(application: CustomApplication): RSNDatabase{
        return Room.databaseBuilder(application, RSNDatabase::class.java, "RNSDatabase").build()
    }

    @Provides
    @Singleton
    fun providePostDao(database: RSNDatabase): PostDao {
        return database.postDao()
    }




}