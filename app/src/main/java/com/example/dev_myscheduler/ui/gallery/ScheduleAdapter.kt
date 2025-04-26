package com.example.dev_myscheduler.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dev_myscheduler.R
import com.google.gson.annotations.SerializedName

data class ScheduleItem(
    @SerializedName("Ziua") val ziua: String,
    @SerializedName("Orele") val orele: String,
    @SerializedName("Sala") val sala: String,
    @SerializedName("Tipul") val tipul: String,
    @SerializedName("Disciplina") val disciplina: String,
    @SerializedName("CD") val cd: String
)


class ScheduleAdapter(private var scheduleList: List<ScheduleItem>) :
    RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    class ScheduleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayTextView: TextView = view.findViewById(R.id.textViewDay)
        val timeTextView: TextView = view.findViewById(R.id.textViewTime)
        val roomTextView: TextView = view.findViewById(R.id.textViewRoom)
        val typeTextView: TextView = view.findViewById(R.id.textViewType)
        val courseTextView: TextView = view.findViewById(R.id.textViewCourse)
        val professorTextView: TextView = view.findViewById(R.id.textViewProfessor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_schedule, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val scheduleItem = scheduleList[position]
        holder.dayTextView.text = scheduleItem.ziua
        holder.timeTextView.text = scheduleItem.orele
        holder.roomTextView.text = "Room: ${scheduleItem.sala}"
        holder.typeTextView.text = "Type: ${scheduleItem.tipul}"
        holder.courseTextView.text = scheduleItem.disciplina
        holder.professorTextView.text = "${scheduleItem.cd}"
    }

    override fun getItemCount() = scheduleList.size

    fun updateSchedule(newList: List<ScheduleItem>) {
        scheduleList = newList
        notifyDataSetChanged()
    }
}

