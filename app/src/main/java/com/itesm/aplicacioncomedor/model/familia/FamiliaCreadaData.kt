package com.itesm.aplicacioncomedor.model.familia

import com.google.gson.annotations.SerializedName

data class FamiliaCreadaData (
    @SerializedName("nombre")
    var nombre: String
)