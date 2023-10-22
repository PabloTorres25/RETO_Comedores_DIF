package com.itesm.aplicacioncomedor.model.asistencia

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query


/*
*   APIS que serán consultadas para Asistentes
*/

interface AsistenteApi {
    @Headers("Content-Type: application/json")
    @POST("obtenerBeneficiarios")
    fun obtenerAsistentes(): Call<List<AsistentesData>>

    @Headers("Content-Type: application/json")
    @POST("obtenerIdBeneficiario")
    fun obtenerBeneficiario(@Query("nombre") nombre: String,
                            @Query("fechaNacimiento") fechaNacimiento: String): Call<Int>

    @Headers("Content-Type: application/json")
    @POST("registrarAsistencia")
    fun registraAsistencia(@Body iSData: AsistenciaRegistroData): Call<Void>

}