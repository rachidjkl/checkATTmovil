package com.example.menulateral.ui.visorAsistencia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VisorAsistenciaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Mario Leiva"
    }
    val text: LiveData<String> = _text
}