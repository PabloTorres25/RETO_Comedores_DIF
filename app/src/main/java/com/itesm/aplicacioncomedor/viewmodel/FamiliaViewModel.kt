package com.itesm.aplicacioncomedor.viewmodel

import androidx.lifecycle.ViewModel
import com.itesm.aplicacioncomedor.model.asistencia.AsistentesData

class FamiliaViewModel : ViewModel() {
    val arrFamilia: MutableList<AsistentesData> = mutableListOf()
}