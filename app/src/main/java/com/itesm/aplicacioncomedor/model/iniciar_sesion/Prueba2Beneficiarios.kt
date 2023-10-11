package com.itesm.aplicacioncomedor.model.iniciar_sesion

import com.google.gson.annotations.SerializedName

data class Prueba2Beneficiarios(

    @SerializedName("idBeneficiario")
    var id: Int,
    @SerializedName("nombre")
    var nombre: String
)
