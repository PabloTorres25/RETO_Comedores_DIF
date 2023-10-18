package com.itesm.aplicacioncomedor.model.registro_nuevo

import com.google.gson.annotations.SerializedName

data class CondicionData(
    @SerializedName("idBeneficiario")
    var idBeneficiario: Int,
    @SerializedName("idCondicion")
    var idCondicion: Int
)
