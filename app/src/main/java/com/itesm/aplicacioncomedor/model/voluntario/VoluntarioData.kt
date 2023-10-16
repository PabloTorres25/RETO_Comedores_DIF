package com.itesm.aplicacioncomedor.model.voluntario

import com.google.gson.annotations.SerializedName

data class VoluntarioData(
    @SerializedName("nombre")
    var nombre: String,
    @SerializedName("fechaNacimiento")
    var fechaNacimiento: String,
    @SerializedName("telefono")
    var telefono: String
)
