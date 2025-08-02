package com.example.dev_myscheduler.ui.gallery

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException


class ScheduleViewModel(application: Application) : AndroidViewModel(application) {

    private val groupUrls: MutableMap<String, String> = mutableMapOf()
    private val client = OkHttpClient()

    private val _scheduleLiveData = MutableLiveData<List<ScheduleItem>>()
    val scheduleLiveData: LiveData<List<ScheduleItem>> = _scheduleLiveData

    private val _logsLiveData = MutableLiveData<List<String>>(mutableListOf())
    val logsLiveData: LiveData<List<String>> = _logsLiveData

    private val _filteredScheduleLiveData = MediatorLiveData<List<ScheduleItem>>()
    val filteredScheduleLiveData: LiveData<List<ScheduleItem>> = _filteredScheduleLiveData

    init {
        fetchAvailableGroups()
        setupFiltering()
        loadScheduleFromStorage()
        loadLogsFromStorage()
    }

    private fun setupFiltering() {
        // Just pass through the schedule directly
        _filteredScheduleLiveData.addSource(_scheduleLiveData) { schedule ->
            _filteredScheduleLiveData.value = schedule
            Log.d("ScheduleViewModel", "Passing through ${schedule.size} items")
        }
    }

    private fun filterScheduleByGroup(schedule: List<ScheduleItem>): List<ScheduleItem> {
        val group = getPreferredGroup().also {
            Log.d("ScheduleViewModel", "Filtering schedule for group: $it")
        }

        val filtered = schedule.filter {
            val matches = it.group == group
            if (!matches) {
                Log.v("ScheduleViewModel", "Filtering out item from group ${it.group} (wanted $group)")
            }
            matches
        }

        Log.d("ScheduleViewModel", "Filter result: ${filtered.size} items")
        return filtered
    }

    fun loadScheduleFromStorage() {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("latest_schedule", null)

        if (json != null) {
            val type = object : TypeToken<List<ScheduleItem>>() {}.type
            val scheduleList: List<ScheduleItem> = Gson().fromJson(json, type)
            _scheduleLiveData.postValue(scheduleList)
        } else {
            fetchScheduleFromApi("811")
        }
    }

    private fun fetchScheduleFromApi(group: String) {
        Log.d("ScheduleViewModel", "Fetching schedule specifically for group: $group")
        val request = Request.Builder()
            .url("https://schedulemonitor.onrender.com/orar/$group")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ScheduleViewModel", "API call failed for group $group", e)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val responseBody = response.body?.string()
                    if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
                        val apiResponse = Gson().fromJson(responseBody, ApiResponse::class.java)
                        Log.d("ScheduleViewModel", "Received ${apiResponse.Orar.size} items for group $group")

                        _scheduleLiveData.postValue(apiResponse.Orar)
                        saveScheduleToStorage(apiResponse.Orar)
                        updateLogs("Fetched schedule for $group at ${apiResponse.UltimaVerificare}")
                    } else {
                        Log.e("ScheduleViewModel", "Empty or unsuccessful response for group $group")
                    }
                } catch (e: Exception) {
                    Log.e("ScheduleViewModel", "Error processing response", e)
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
        val logs = _logsLiveData.value?.toMutableList() ?: mutableListOf()
        logs.add("Checked at: $timestamp")
        _logsLiveData.postValue(logs)
        saveLogsToStorage(logs)
    }

    private fun loadLogsFromStorage() {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val logs = sharedPreferences.getStringSet("logs", mutableSetOf())?.toList() ?: emptyList()
        _logsLiveData.postValue(logs)
    }

    private fun saveLogsToStorage(logs: List<String>) {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putStringSet("logs", logs.toSet())
        editor.apply()
    }

    private fun fetchAvailableGroups() {
        val request = Request.Builder()
            .url("https://schedulemonitor.onrender.com/rescan")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ScheduleViewModel", "Failed to fetch group list", e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { body ->
                    val json = Gson().fromJson(body, Map::class.java)
                    val groups = json["Available groups"] as? Map<String, String>
                    groups?.let {
                        groupUrls.putAll(it)
                        fetchScheduleForPreferredGroup()
                    }
                }
            }
        })
    }

    private fun fetchScheduleForPreferredGroup() {
        getPreferredGroup()?.let { fetchScheduleFromApi(it) }
    }

    fun getPreferredGroup(): String? {
        val prefs = getApplication<Application>().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return prefs.getString("preferred_group", null)
    }

    fun getAvailableGroups(): List<String> = groupUrls.keys.sorted()

    fun setPreferredGroup(group: String) {
        val prefs = getApplication<Application>().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        prefs.edit().putString("preferred_group", group).apply()
        fetchScheduleFromApi(group)
    }
}
