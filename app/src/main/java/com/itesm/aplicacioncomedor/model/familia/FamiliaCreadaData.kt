package com.itesm.aplicacioncomedor.model.familia

import com.google.gson.annotations.SerializedName
/*
* Representa el nombre de la familia
*/
data class FamiliaCreadaData (
    @SerializedName("nombre")
    var nombre: String
)