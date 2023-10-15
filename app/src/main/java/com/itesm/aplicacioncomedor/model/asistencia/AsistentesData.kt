package com.itesm.aplicacioncomedor.model.asistencia

import com.google.gson.annotations.SerializedName

data class AsistentesData(
    @SerializedName("nombre")
    var nombre: String,
    @SerializedName("fehaNacimiento")
    var fechaNacimiento: String
)