package com.example.dev_myscheduler.ui.slideshow

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dev_myscheduler.R
import com.example.dev_myscheduler.network.StudentGrade

class GradesAdapter(private var grades: List<StudentGrade>) :
    RecyclerView.Adapter<GradesAdapter.GradeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GradeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_grade, parent, false)
        return GradeViewHolder(view)
    }

    override fun onBindViewHolder(holder: GradeViewHolder, position: Int) {
        holder.bind(grades[position])
        Log.d("GradesAdapter", "Binding grade: ${grades[position]}")
    }

    override fun getItemCount(): Int = grades.size

    fun updateGrades(newGrades: List<StudentGrade>?) {
        if (newGrades != null) {
            grades = newGrades
            Log.d("GradesAdapter", "Updated grades list: $grades")
        }
        notifyDataSetChanged()
    }

    inner class GradeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val disciplineText: TextView = itemView.findViewById(R.id.textViewDiscipline)
        private val gradeText: TextView = itemView.findViewById(R.id.textViewGrade)
        private val creditsText: TextView = itemView.findViewById(R.id.textViewCredits)

        fun bind(grade: StudentGrade) {
            disciplineText.text = "Disciplina: ${grade.disciplina}"
            gradeText.text = "Nota: ${grade.nota}"
            creditsText.text = "An studiu: ${grade.anStudiu}"
            Log.d("GradesAdapter", "Binding grade: ${grade.disciplina} - ${grade.nota}")
        }
    }
}
