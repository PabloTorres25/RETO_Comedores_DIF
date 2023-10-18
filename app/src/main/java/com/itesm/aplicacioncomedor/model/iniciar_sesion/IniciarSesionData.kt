package com.itesm.aplicacioncomedor.model.iniciar_sesion

import com.google.gson.annotations.SerializedName

data class IniciarSesionData(
    @SerializedName("nombre")
    var usuario: String,
    @SerializedName("contrasena")
    var contrasena: String
)
