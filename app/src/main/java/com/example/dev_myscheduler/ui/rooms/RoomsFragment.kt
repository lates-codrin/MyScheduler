package com.example.dev_myscheduler.ui.rooms

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dev_myscheduler.databinding.FragmentRoomsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Callback
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException



import android.widget.Toast
import androidx.appcompat.widget.SearchView


class RoomsFragment : Fragment() {

    private var _binding: FragmentRoomsBinding? = null
    private val binding get() = _binding!!

    private lateinit var roomAdapter: RoomAdapter
    private var roomsList = mutableListOf<Room>()
    private var filteredRoomsList = mutableListOf<Room>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoomsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roomAdapter = RoomAdapter(
            filteredRoomsList,
            context = requireContext()
        ) { location ->
            val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(location)}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        // Set up RecyclerView
        binding.roomRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.roomRecyclerView.adapter = roomAdapter

        // Fetch the room data from the API
        fetchRooms()

        // Set up search functionality
        binding.searchRoomsView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter rooms based on query text
                filterRooms(newText)
                return true
            }
        })
    }

    // Function to fetch room data from the API
    private fun fetchRooms() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://schedulemonitor.onrender.com/rooms") // Your API endpoint
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { body ->
                    try {
                        val jsonArray = JSONArray(body)
                        roomsList.clear()

                        for (i in 0 until jsonArray.length()) {
                            val obj = jsonArray.getJSONObject(i)
                            roomsList.add(
                                Room(
                                    salaName = obj.getString("salaName"),
                                    salaLocation = obj.getString("salaLocation"),
                                    salaDescription = "no"
                                )
                            )
                        }

                        // Initially, display all rooms
                        filteredRoomsList.clear()
                        filteredRoomsList.addAll(roomsList)

                        // Update RecyclerView on the main thread
                        activity?.runOnUiThread {
                            roomAdapter.notifyDataSetChanged()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    // Function to filter the rooms based on search query
    private fun filterRooms(query: String?) {
        filteredRoomsList.clear()
        if (query.isNullOrEmpty()) {
            filteredRoomsList.addAll(roomsList)
        } else {
            for (room in roomsList) {
                if (room.salaName.contains(query, ignoreCase = true) || room.salaLocation.contains(query, ignoreCase = true)) {
                    filteredRoomsList.add(room)
                }
            }
        }

        // Notify the adapter that the data set has changed
        roomAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
