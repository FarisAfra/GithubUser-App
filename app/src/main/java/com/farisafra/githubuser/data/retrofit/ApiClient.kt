package com.farisafra.githubuser.data.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val mySuperScretKey = com.farisafra.githubuser.BuildConfig.KEY

    private val authInterceptor = Interceptor { chain ->
        val req = chain.request()
        val requestHeaders = req.newBuilder()
            .addHeader("Authorization", "token" + mySuperScretKey)
            .build()
        chain.proceed(requestHeaders)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    private const val BASE_URL = com.farisafra.githubuser.BuildConfig.BASE_URL

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val apiInstance = retrofit.create(ApiService::class.java)
}