package com.itesm.aplicacioncomedor.model.registro_nuevo

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RegistroApi {
    @Headers("Content-Type: application/json")
    @POST("agregarBeneficiario")
    fun autentificaUsuario(@Body agregarBenf: RegistroDataClass): Call<Void>
}