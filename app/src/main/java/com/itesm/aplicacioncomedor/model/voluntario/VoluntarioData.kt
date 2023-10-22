package com.itesm.aplicacioncomedor.model.voluntario

import com.google.gson.annotations.SerializedName

/*
*   Datos que son pedidos dentro de "agregarVoluntario"
*   Representa la información de un nuevo Voluntario
*/
data class VoluntarioData(
    @SerializedName("nombre")
    var nombre: String,
    @SerializedName("fechaNacimiento")
    var fechaNacimiento: String,
    @SerializedName("telefono")
    var telefono: String
)
