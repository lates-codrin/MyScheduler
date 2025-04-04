package com.example.dev_myscheduler.ui.slideshow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dev_myscheduler.R

data class StudentGrade(
    val name: String,
    val subject: String,
    val grade: String
)

class StudentGradeAdapter(private var studentGrades: List<StudentGrade>) :
    RecyclerView.Adapter<StudentGradeAdapter.StudentGradeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentGradeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_logs, parent, false)
        return StudentGradeViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentGradeViewHolder, position: Int) {
        holder.bind(studentGrades[position])
    }

    override fun getItemCount(): Int = studentGrades.size

    fun updateGrades(newGrades: List<StudentGrade>) {
        studentGrades = newGrades
        notifyDataSetChanged()
    }

    inner class StudentGradeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameText: TextView = view.findViewById(R.id.textViewStudentName)
        private val subjectText: TextView = view.findViewById(R.id.textViewSubject)
        private val gradeText: TextView = view.findViewById(R.id.textViewGrade)

        fun bind(student: StudentGrade) {
            nameText.text = student.name
            subjectText.text = student.subject
            gradeText.text = student.grade
        }
    }
}
