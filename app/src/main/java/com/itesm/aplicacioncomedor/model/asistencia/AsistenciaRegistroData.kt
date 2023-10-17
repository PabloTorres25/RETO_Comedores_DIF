package com.itesm.aplicacioncomedor.model.asistencia

import com.google.gson.annotations.SerializedName

data class AsistenciaRegistroData
    (
    @SerializedName("idComedor")
    var idComedor: Int,
    @SerializedName("idBeneficiario")
    var idBeneficiario: Int,
    @SerializedName("fecha")
    var fecha: String,
    @SerializedName("comidaPagada")
    var pagado: String,
    @SerializedName("presente")
    var presente: String
            )