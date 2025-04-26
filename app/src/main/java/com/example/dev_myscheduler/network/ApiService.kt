package com.example.dev_myscheduler.network

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("/start-login")
    fun startLogin(@Query("user_id") userId: String): Call<StartLoginResponse>



    @POST("/solve-captcha")
    fun solveCaptcha(
        @Query("user_id") userId: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("captcha_token") captchaToken: String
    ): Call<List<StudentGrade>>

}
