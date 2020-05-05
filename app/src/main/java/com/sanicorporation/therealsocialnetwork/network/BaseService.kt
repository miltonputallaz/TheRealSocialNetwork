package com.sanicorporation.therealsocialnetwork.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BaseService {
    companion object {
        private  val BASE_URL = "http://192.168.1.6:3001/"

        val client = OkHttpClient
            .Builder()
            .addInterceptor(BaseInterceptor())
            .build()


        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();
    }

}