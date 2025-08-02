package com.example.dev_myscheduler.ui.privacy_policy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dev_myscheduler.databinding.FragmentPrivacyBinding

class PrivacyFragment : Fragment() {

    private var _binding: FragmentPrivacyBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val PrivacyViewModel =
            ViewModelProvider(this).get(PrivacyViewModel::class.java)

        _binding = FragmentPrivacyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textPrivacy
        PrivacyViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}