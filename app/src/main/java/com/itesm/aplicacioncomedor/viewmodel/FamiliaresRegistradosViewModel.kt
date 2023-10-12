package com.itesm.aplicacioncomedor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itesm.aplicacioncomedor.model.asistencia.Asistentes

class FamiliaresRegistradosViewModel : ViewModel() {
    private val _dataList = MutableLiveData<List<Asistentes>>() // Tu modelo de datos


}
