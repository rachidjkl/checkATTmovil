package com.example.menulateral.ui.justificarFalta

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JustificarFaltaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "justificar"
    }

    val text: LiveData<String> = _text
}