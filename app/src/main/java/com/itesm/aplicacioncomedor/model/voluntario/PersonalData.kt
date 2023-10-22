package com.itesm.aplicacioncomedor.model.voluntario

import com.google.gson.annotations.SerializedName

/*
*   Datos que son pedidos dentro de "agregarPersonal"
*   Representa la información de que rol tiene el voluntario
*/
data class PersonalData(
    @SerializedName("idComedor")
    var idComedor: Int,
    @SerializedName("idVoluntario")
    var idVoluntario: Int,
    @SerializedName("rol")
    var rol: String
)
