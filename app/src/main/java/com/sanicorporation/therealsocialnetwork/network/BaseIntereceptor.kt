package com.sanicorporation.therealsocialnetwork.network

import com.sanicorporation.therealsocialnetwork.CustomApplication
import com.sanicorporation.therealsocialnetwork.utils.Keys
import com.sanicorporation.therealsocialnetwork.utils.Preferences
import okhttp3.Interceptor
import okhttp3.Response

class BaseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run{
        val original = chain.request()

        // Request customization: add request headers
        var requestBuilder = original.newBuilder()

        val token: String? = Preferences.INSTANCE.getString(CustomApplication.instance, Keys.UID.toString(),null)
        token?.let {
            requestBuilder.addHeader("firebasetoken",it).build()
        }

        val request = requestBuilder.build()
        chain.proceed(request)

    }

}