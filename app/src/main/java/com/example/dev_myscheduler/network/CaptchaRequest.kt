package com.example.dev_myscheduler.network

import com.google.gson.annotations.SerializedName

data class CaptchaRequest(
    @SerializedName("user_id") val userId: String,
    val username: String,
    val password: String,
    @SerializedName("captcha_token") val captchaToken: String
)
