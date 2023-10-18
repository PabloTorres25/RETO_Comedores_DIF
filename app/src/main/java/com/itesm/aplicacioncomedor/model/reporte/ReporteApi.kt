package com.itesm.aplicacioncomedor.model.reporte

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ReporteApi {
    @Headers("Content-Type: application/json")
    @POST("registrarAviso")
    fun postAviso(@Body iSData: ReporteData): Call<Void>
}