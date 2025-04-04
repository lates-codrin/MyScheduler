package com.example.dev_myscheduler.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dev_myscheduler.databinding.FragmentSlideshowBinding

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: StudentGradesViewModel
    private lateinit var adapter: StudentGradeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root = binding.root

        adapter = StudentGradeAdapter(emptyList())
        binding.recyclerViewStudentGrades.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewStudentGrades.adapter = adapter

        viewModel = ViewModelProvider(this)[StudentGradesViewModel::class.java]

        viewModel.grades.observe(viewLifecycleOwner) {
            adapter.updateGrades(it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
