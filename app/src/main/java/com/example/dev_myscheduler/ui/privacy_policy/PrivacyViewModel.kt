package com.example.dev_myscheduler.ui.privacy_policy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PrivacyViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val _text = MutableLiveData<String>().apply {
        value = "This is privacy Fragment"
    }
    val text: LiveData<String> = _text
}

