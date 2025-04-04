package com.example.dev_myscheduler.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dev_myscheduler.databinding.FragmentGalleryBinding

class ScheduleFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var scheduleViewModel: ScheduleViewModel
    private lateinit var scheduleAdapter: ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root = binding.root

        scheduleAdapter = ScheduleAdapter(emptyList())
        binding.recyclerViewSchedule.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSchedule.adapter = scheduleAdapter

        scheduleViewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)

        scheduleViewModel.scheduleLiveData.observe(viewLifecycleOwner) { scheduleList ->
            scheduleAdapter.updateSchedule(scheduleList)
        }

        scheduleViewModel.loadScheduleFromStorage()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
