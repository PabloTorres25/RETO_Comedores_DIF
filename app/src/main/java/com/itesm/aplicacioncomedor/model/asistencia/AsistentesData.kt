package com.itesm.aplicacioncomedor.model.asistencia

import com.google.gson.annotations.SerializedName
/*
*   Datos que son pedidos dentro de "obtenerBeneficiarios"
*   Representa la información de una beneficiario
*/
data class AsistentesData(
    @SerializedName("nombre")
    var nombre: String,
    @SerializedName("fechaNacimiento")
    var fechaNacimiento: String
)
