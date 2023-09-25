package com.itesm.aplicacioncomedor.ui.voluntario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Hola"
    }
    val text: LiveData<String> = _text
}