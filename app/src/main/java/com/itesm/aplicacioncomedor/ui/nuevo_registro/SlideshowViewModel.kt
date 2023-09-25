package com.itesm.aplicacioncomedor.ui.nuevo_registro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SlideshowViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is nuevo registro Fragment"
    }
    val text: LiveData<String> = _text
}