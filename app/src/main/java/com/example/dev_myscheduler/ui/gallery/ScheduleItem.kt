package com.example.dev_myscheduler.ui.gallery

import com.google.gson.annotations.SerializedName

data class ScheduleItem(
    @SerializedName("Ziua") val day: String?,
    @SerializedName("Orele") val hour: String?,
    @SerializedName("Sala") val room: String?,
    @SerializedName("Disciplina") val subject: String?,
    @SerializedName("CD") val professor: String?,
    @SerializedName("Formatia") val group: String?,
    @SerializedName("Frecventa") val frequency: String? = null,
    @SerializedName("Tipul") val type: String? = null
)