package com.example.dev_myscheduler.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClient {
    private const val BASE_URL = "https://api.latescodrin.com/"

    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val instance: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
