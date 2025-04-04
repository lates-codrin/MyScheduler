package com.example.dev_myscheduler.ui.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudentGradesViewModel : ViewModel() {

    private val _grades = MutableLiveData<List<StudentGrade>>()
    val grades: LiveData<List<StudentGrade>> = _grades

    init {
        loadGrades()
    }

    private fun loadGrades() {
        _grades.value = listOf(
            StudentGrade("Alice", "Math", "A"),
            StudentGrade("Bob", "History", "B+"),
            StudentGrade("Charlie", "Science", "A-"),
            StudentGrade("Dana", "English", "B"),
            StudentGrade("Eve", "Physics", "A+"),
            StudentGrade("Alice", "Math", "A"),
            StudentGrade("Bob", "History", "B+"),
            StudentGrade("Charlie", "Science", "A-"),
            StudentGrade("Dana", "English", "B"),
            StudentGrade("Alice", "Math", "A"),
            StudentGrade("Bob", "History", "B+"),
            StudentGrade("Charlie", "Science", "A-"),
            StudentGrade("Dana", "English", "B")
        )
    }
}
