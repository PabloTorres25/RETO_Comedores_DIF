package com.itesm.aplicacioncomedor.model.familia

import com.google.gson.annotations.SerializedName

data class FamiliaData(
    @SerializedName("idFamilia")
    var idFamilia: Int,
    @SerializedName("idBeneficiario")
    var idBeneficiario: Int)
