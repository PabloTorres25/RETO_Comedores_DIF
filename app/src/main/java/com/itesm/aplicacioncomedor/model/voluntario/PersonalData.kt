package com.itesm.aplicacioncomedor.model.voluntario

import com.google.gson.annotations.SerializedName

data class PersonalData(
    @SerializedName("idComedor")
    var idComedor: Int,
    @SerializedName("idVoluntario")
    var idVoluntario: Int,
    @SerializedName("rol")
    var rol: String
)
