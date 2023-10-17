package com.itesm.aplicacioncomedor.model.reporte

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ReporteApi {
    @Headers("Content-Type: application/json")
    @POST("registrarAviso")
    fun postAviso(@Body iSData: ReporteData): Call<Void>
}