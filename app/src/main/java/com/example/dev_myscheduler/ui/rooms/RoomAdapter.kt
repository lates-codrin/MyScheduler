package com.example.dev_myscheduler.ui.rooms

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dev_myscheduler.R

class RoomAdapter(
    private val rooms: List<Room>,
    private val context: Context,
    private val onLocationClick: (String) -> Unit
) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    inner class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val roomName: TextView = view.findViewById(R.id.text_article_title)
        val roomLocation: TextView = view.findViewById(R.id.text_article_date)
        val openInMapsButton: Button = view.findViewById(R.id.button_read_more)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = rooms[position]

        holder.roomName.text = room.salaName
        holder.roomLocation.text = room.salaLocation

        // Make title and location bold
        holder.roomName.setTypeface(null, android.graphics.Typeface.BOLD)
        holder.roomLocation.setTypeface(null, android.graphics.Typeface.BOLD)

        // Handle the "Open in Google Maps" button click
        holder.openInMapsButton.setOnClickListener {
            onLocationClick(room.salaLocation)
        }
    }

    override fun getItemCount(): Int = rooms.size
}
