package com.itesm.aplicacioncomedor.model.registro_nuevo

import com.google.gson.annotations.SerializedName
/*
*   Datos que son pedidos dentro de "agregarCondicionBeneficiario"
*   Representa la información sobre las condiciones de un beneficiario
*/
data class CondicionData(
    @SerializedName("idBeneficiario")
    var idBeneficiario: Int,
    @SerializedName("idCondicion")
    var idCondicion: Int
)
