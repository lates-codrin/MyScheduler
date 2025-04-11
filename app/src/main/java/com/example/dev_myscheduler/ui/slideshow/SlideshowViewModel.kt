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
            StudentGrade("Seminar", "Algebra 2", "10"),
            StudentGrade("Laborator", "Structuri de date", "9"),
            StudentGrade("Seminar", "Analiza 2", "10"),
            StudentGrade("Seminar", "Geometrie 2", "9"),
            StudentGrade("Laborator", "OOP", "10"),
            StudentGrade("Laborator", "Structuri de date", "10"),
            StudentGrade("Laborator", "OOP", "9"),
            StudentGrade("Seminar", "Algebra 2", "10"),
            StudentGrade("Laborator", "OOP", "9"),
            StudentGrade("Seminar", "Geometrie 2", "10"),
            StudentGrade("Laborator", "OOP", "9"),
            StudentGrade("Laborator", "Structuri de date", "10"),
            StudentGrade("Seminar", "Analiza 2", "9")
        )
    }
}
