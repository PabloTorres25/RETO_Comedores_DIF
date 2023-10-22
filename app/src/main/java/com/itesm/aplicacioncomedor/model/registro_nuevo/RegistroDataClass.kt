package com.itesm.aplicacioncomedor.model.registro_nuevo

import com.google.gson.annotations.SerializedName
/*
*   Datos que son pedidos dentro de "agregarBeneficiario"
*   Representa la información de un nuevo registro
*/
data class RegistroDataClass(
    @SerializedName("nombre")
    var nombreBenef: String,
    @SerializedName("curp")
    var curp: String,
    @SerializedName("fechaNacimiento")
    var fecha: String,
    @SerializedName("sexo")
    var sexo: String,
    @SerializedName("calle")
    var calle: String,
    @SerializedName("colonia")
    var colonia: String,
    @SerializedName("municipio")
    var municipio: String
)
