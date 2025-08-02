package com.example.dev_myscheduler.ui.credentials

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dev_myscheduler.databinding.FragmentCredentialsBinding

class CredentialsFragment : Fragment() {

    private var _binding: FragmentCredentialsBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val CredentialsViewModel =
            ViewModelProvider(this).get(CredentialsViewModel::class.java)

        _binding = FragmentCredentialsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCredentials
        CredentialsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}