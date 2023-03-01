package com.example.menulateral.ui.verfaltas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VerFaltasViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is ver faltas Fragment"

    }
    val text: LiveData<String> = _text
}