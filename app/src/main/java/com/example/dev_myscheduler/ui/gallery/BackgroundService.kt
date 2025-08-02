package com.example.dev_myscheduler.ui.gallery

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.dev_myscheduler.R
import okhttp3.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.IOException
import kotlin.jvm.java
import kotlin.ranges.random

data class ApiResponse(
    val Grupa: Int,
    val Orar: List<ScheduleItem>,
    val Code: Int,
    val UltimaVerificare: String = ""
)
class BackgroundService : Service() {

    private val handler = Handler()
    private val delayMin = 10000L // 10 seconds
    private val delayMax = 20000L // 20 seconds
    private var previousTimestamp: String? = null
    private val client = OkHttpClient()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(1, getNotification("Service is running..."))
        startLoop()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun startLoop() {
        val delay = (delayMin..delayMax).random()
        handler.postDelayed({
            fetchAndNotify()
            startLoop()
        }, delay)
    }

    private fun fetchAndNotify() {
        val request = Request.Builder()
            .url("https://schedulemonitor.onrender.com/orar/811")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("com.example.dev_myscheduler.ui.gallery.BackgroundService", "Network request failed", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (responseBody != null) {
                    val apiResponse = Gson().fromJson(responseBody, ApiResponse::class.java)

                    saveLog("Checked at: ${apiResponse.UltimaVerificare}")

                    if (apiResponse.UltimaVerificare != previousTimestamp) {
                        previousTimestamp = apiResponse.UltimaVerificare
                        saveScheduleData(apiResponse.Orar)
                    }
                }
            }
        })
    }

    private fun saveScheduleData(scheduleList: List<ScheduleItem>) {
        val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(scheduleList)
        editor.putString("latest_schedule", json)
        editor.apply()
    }

    private fun saveLog(logMessage: String) {
        val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val logs = sharedPreferences.getStringSet("logs", mutableSetOf()) ?: mutableSetOf()

        logs.add(logMessage)
        editor.putStringSet("logs", logs)
        editor.apply()
    }

    private fun showNotification(message: String) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notification = getNotification(message)
        notificationManager.notify(2, notification)
    }

    private fun getNotification(message: String): Notification {
        return NotificationCompat.Builder(this, "background_service")
            .setContentTitle("Schedule Update")
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "background_service",
                "Background Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }
}
