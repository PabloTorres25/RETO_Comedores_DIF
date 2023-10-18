package com.itesm.aplicacioncomedor.model.familia

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface FamiliaApi {
    @Headers("Content-Type: application/json")
    @POST("registrarFamilia")
    fun registrarFamilia(@Body iSData: FamiliaCreadaData): Call<Void>

    @Headers("Content-Type: application/json")
    @POST("registrarFamiliaBeneficiario")
    fun agregarFamiliaBeneficiario(@Body iSData: FamiliaData): Call<Void>

    @GET("obtenerIdFamiliaCreada")
    fun obtenerIdFamilia(@Query("nombre") nombre: String): Call<Int>


}