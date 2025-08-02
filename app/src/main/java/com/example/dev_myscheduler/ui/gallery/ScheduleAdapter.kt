package com.example.dev_myscheduler.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dev_myscheduler.databinding.ItemScheduleBinding

class ScheduleAdapter : ListAdapter<ScheduleItem, ScheduleAdapter.ScheduleViewHolder>(ScheduleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ItemScheduleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ScheduleViewHolder(private val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ScheduleItem) {
            with(binding) {
                textViewDay.text = item.day.orEmpty()
                textViewTime.text = item.hour.orEmpty()
                textViewRoom.text = item.room?.let { "Room: $it" }.orEmpty()
                textViewCourse.text = item.subject.orEmpty()
                textViewProfessor.text = item.professor?.let { "Prof: $it" }.orEmpty()
                textViewType.text = item.type.orEmpty()
                textViewGroup.text = item.group?.let { "Group: $it" }.orEmpty()

                // Set visibility based on data
                textViewType.visibility = if (item.type.isNullOrEmpty()) View.GONE else View.VISIBLE
                textViewGroup.visibility = if (item.group.isNullOrEmpty()) View.GONE else View.VISIBLE
            }
        }
    }

    class ScheduleDiffCallback : DiffUtil.ItemCallback<ScheduleItem>() {
        override fun areItemsTheSame(oldItem: ScheduleItem, newItem: ScheduleItem): Boolean {
            return oldItem.day == newItem.day &&
                    oldItem.hour == newItem.hour &&
                    oldItem.subject == newItem.subject
        }

        override fun areContentsTheSame(oldItem: ScheduleItem, newItem: ScheduleItem): Boolean {
            return oldItem == newItem
        }
    }
}