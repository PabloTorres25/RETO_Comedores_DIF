package com.itesm.aplicacioncomedor.model.familia

import com.google.gson.annotations.SerializedName

/*
* Representa que beneficiario pertenece a "que" familia.
*/

data class FamiliaData(
    @SerializedName("idFamilia")
    var idFamilia: Int,
    @SerializedName("idBeneficiario")
    var idBeneficiario: Int)
