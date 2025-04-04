package com.example.dev_myscheduler.ui.gallery

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {

    val scheduleLiveData: MutableLiveData<List<ScheduleItem>> = MutableLiveData()
    val logsLiveData: MutableLiveData<List<String>> = MutableLiveData(mutableListOf())
    private val client = OkHttpClient()

    init {
        loadScheduleFromStorage()
        loadLogsFromStorage()
    }

    fun loadScheduleFromStorage() {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("latest_schedule", null)

        if (json != null) {
            val type = object : TypeToken<List<ScheduleItem>>() {}.type
            val scheduleList: List<ScheduleItem> = Gson().fromJson(json, type)
            scheduleLiveData.postValue(scheduleList)
        } else {
            fetchScheduleFromApi()
        }
    }

    private fun fetchScheduleFromApi() {
        val request = Request.Builder()
            .url("https://schedulemonitor.onrender.com/orar/811")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ScheduleViewModel", "Failed to fetch schedule", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (responseBody != null) {
                    val apiResponse = Gson().fromJson(responseBody, ApiResponse::class.java)
                    scheduleLiveData.postValue(apiResponse.Orar)
                    saveScheduleToStorage(apiResponse.Orar)
                    updateLogs(apiResponse.UltimaVerificare)
                }
            }
        })
    }

    private fun saveScheduleToStorage(scheduleList: List<ScheduleItem>) {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(scheduleList)
        editor.putString("latest_schedule", json)
        editor.apply()
    }

    private fun updateLogs(timestamp: String) {
        val logs = logsLiveData.value?.toMutableList() ?: mutableListOf()
        logs.add("Checked at: $timestamp")
        logsLiveData.postValue(logs)
        saveLogsToStorage(logs)
    }

    private fun loadLogsFromStorage() {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val logs = sharedPreferences.getStringSet("logs", mutableSetOf())?.toList() ?: emptyList()
        logsLiveData.postValue(logs)
    }

    private fun saveLogsToStorage(logs: List<String>) {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putStringSet("logs", logs.toSet())
        editor.apply()
    }
}
