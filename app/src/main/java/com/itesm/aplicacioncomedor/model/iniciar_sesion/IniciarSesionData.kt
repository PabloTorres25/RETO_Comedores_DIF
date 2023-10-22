package com.itesm.aplicacioncomedor.model.iniciar_sesion

import com.google.gson.annotations.SerializedName
/*
*   Datos que son pedidos dentro de "autentificaUsuario"
*   Representa la información para autentificar a un usuario
*/
data class IniciarSesionData(
    @SerializedName("nombre")
    var usuario: String,
    @SerializedName("contrasena")
    var contrasena: String
)
