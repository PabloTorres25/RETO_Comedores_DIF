package com.itesm.aplicacioncomedor.model.registro_nuevo

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface RegistroApi {
    @Headers("Content-Type: application/json")
    @POST("agregarBeneficiario")
    fun agregarBeneficiario(@Body agregarBenf: RegistroDataClass): Call<Void>

    @Headers("Content-Type: application/json")
    @POST("agregarCondicionBeneficiario")
    fun agregarCondicion(@Body iSData: CondicionData): Call<Void>

}