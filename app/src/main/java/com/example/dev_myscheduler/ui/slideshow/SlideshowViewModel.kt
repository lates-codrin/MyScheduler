package com.example.dev_myscheduler.ui.slideshow

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dev_myscheduler.network.RetrofitClient
import com.example.dev_myscheduler.network.StartLoginResponse
import com.example.dev_myscheduler.network.StudentGrade
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentGradesViewModel(application: Application) : AndroidViewModel(application) {

    private val _grades = MutableLiveData<List<StudentGrade>>()
    val grades: LiveData<List<StudentGrade>> = _grades

    var onSiteKeyReceived: ((String) -> Unit)? = null

    fun loadGrades() {
        val userId = "-"

        RetrofitClient.instance.startLogin(userId).enqueue(object : Callback<StartLoginResponse> {
            override fun onResponse(call: Call<StartLoginResponse>, response: Response<StartLoginResponse>) {
                if (response.isSuccessful) {
                    val sitekey = response.body()?.sitekey ?: return
                    Log.d("StudentGradesViewModel", "Sitekey obtained: $sitekey")
                    onSiteKeyReceived?.invoke(sitekey)
                } else {
                    Log.e("StudentGradesViewModel", "Failed to get sitekey: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<StartLoginResponse>, t: Throwable) {
                Log.e("StudentGradesViewModel", "API call failed: ${t.message}")
            }
        })
    }

    fun solveCaptchaAndFetchGrades(userId: String, username: String, password: String, token: String) {
        RetrofitClient.instance.solveCaptcha(userId, username, password, token)
            .enqueue(object : Callback<List<StudentGrade>> {
                override fun onResponse(call: Call<List<StudentGrade>>, response: Response<List<StudentGrade>>) {
                    if (response.isSuccessful) {
                        val grades = response.body()
                        _grades.postValue(grades ?: emptyList())
                    } else {
                        Log.e("Grades", "Failed to fetch grades: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<StudentGrade>>, t: Throwable) {
                    Log.e("Grades", "Error fetching grades: ${t.message}")
                }
            })
    }

}
