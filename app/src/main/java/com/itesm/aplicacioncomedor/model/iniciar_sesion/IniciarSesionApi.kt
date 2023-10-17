package com.itesm.aplicacioncomedor.model.iniciar_sesion

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface IniciarSesionApi {
    @Headers("Content-Type: application/json")
    @POST("loginComedor")
    fun autentificaUsuario(@Body iSData: IniciarSesionData): Call<Void>

    @GET("obtenerComedor")
    fun obtenerIdComedor(@Query("nombre") nombre: String): Call<Int>

}
