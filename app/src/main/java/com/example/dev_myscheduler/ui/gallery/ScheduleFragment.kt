package com.example.dev_myscheduler.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dev_myscheduler.databinding.FragmentGalleryBinding

class ScheduleFragment : Fragment() {

    private lateinit var binding: FragmentGalleryBinding
    private lateinit var scheduleViewModel: ScheduleViewModel
    private lateinit var scheduleAdapter: ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
        setupObservers()
        setupEmptyState()
    }

    private fun setupViewModel() {
        scheduleViewModel = ViewModelProvider(requireActivity()).get(ScheduleViewModel::class.java)
    }

    private fun setupRecyclerView() {
        scheduleAdapter = ScheduleAdapter()
        binding.recyclerViewSchedule.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = scheduleAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupObservers() {
        scheduleViewModel.filteredScheduleLiveData.observe(viewLifecycleOwner) { schedule ->
            if (schedule.isNullOrEmpty()) {
                showEmptyState()
            } else {
                showSchedule()
                scheduleAdapter.submitList(schedule)
            }
        }
    }

    private fun setupEmptyState() {
        binding.btnSelectGroup.setOnClickListener {
            showGroupSelectionDialog()
        }
    }

    private fun showGroupSelectionDialog() {
        val groups = scheduleViewModel.getAvailableGroups()

        AlertDialog.Builder(requireContext())
            .setTitle("Select your group")
            .setItems(groups.toTypedArray()) { _, which ->
                val selectedGroup = groups[which]
                scheduleViewModel.setPreferredGroup(selectedGroup)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEmptyState() {
        binding.recyclerViewSchedule.visibility = View.GONE
        binding.emptyStateView.visibility = View.VISIBLE
    }

    private fun showSchedule() {
        binding.recyclerViewSchedule.visibility = View.VISIBLE
        binding.emptyStateView.visibility = View.GONE
    }
}