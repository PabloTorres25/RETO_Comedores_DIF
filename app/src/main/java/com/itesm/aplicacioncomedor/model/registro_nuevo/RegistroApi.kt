package com.itesm.aplicacioncomedor.model.registro_nuevo

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface RegistroApi {
    @Headers("Content-Type: application/json")
    @POST("agregarBeneficiario")
    fun autentificaUsuario(@Body agregarBenf: RegistroDataClass): Call<Void>

    @Headers("Content-Type: application/json")
    @POST("agregarCondicionBeneficiario")
    fun agregarCondicion(@Query("idBeneficiario") idBeneficiario: Int,
                         @Query("idCondicion") idCondicion: Int): Call<Void>

}