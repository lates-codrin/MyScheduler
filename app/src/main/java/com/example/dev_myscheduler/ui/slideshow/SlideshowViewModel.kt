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
            StudentGrade("Seminar", "Algebra 2", "A"),
            StudentGrade("Laborator", "Structuri de date", "B+"),
            StudentGrade("Seminar", "Analiza 2", "A-"),
            StudentGrade("Seminar", "Geometrie 2", "B"),
            StudentGrade("Laborator", "OOP", "A+"),
            StudentGrade("Laborator", "Structuri de date", "A"),
            StudentGrade("Laborator", "OOP", "B+"),
            StudentGrade("Seminar", "Algebra 2", "A-"),
            StudentGrade("Laborator", "OOP", "B"),
            StudentGrade("Seminar", "Geometrie 2", "A"),
            StudentGrade("Laborator", "OOP", "B+"),
            StudentGrade("Laborator", "Structuri de date", "A-"),
            StudentGrade("Seminar", "Analiza 2", "B")
        )
    }
}
