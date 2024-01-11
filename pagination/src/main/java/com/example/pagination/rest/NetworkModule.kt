package com.example.pagination.rest

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object NetworkModule {

//    val interceptor = Interceptor {
//        val token: String? = PreferencesManager.token?.ifEmpty { null }
//        val request = it.request().newBuilder().addHeader("Content-Type", CONTENT_TYPE)
//        if (!token.isNullOrEmpty()) {
//            request.addHeader("Authorization", "Token $token")
//        }
//        request.addHeader("Platform", "android")
//        return@Interceptor it.proceed(request.build())
//    }

    private val okHttpClientClient = OkHttpClient.Builder()
        .connectTimeout(45, TimeUnit.SECONDS)
        .readTimeout(45, TimeUnit.SECONDS)
        .writeTimeout(45, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    fun dummyJsonRetrofit(): Retrofit = Retrofit.Builder()
        .client(okHttpClientClient)
        .baseUrl("https://dummyjson.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}