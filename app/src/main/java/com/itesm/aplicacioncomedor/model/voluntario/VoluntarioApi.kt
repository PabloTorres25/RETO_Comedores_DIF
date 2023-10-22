package com.itesm.aplicacioncomedor.model.voluntario

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

/*
*   APIS que serán consultadas para crear un Voluntario
*/
interface VoluntarioApi
{
    @Headers("Content-Type: application/json")
    @POST("agregarVoluntario")
    fun postVoluntario(@Body iSData: VoluntarioData): Call<Void>
    @POST("obtenerVoluntario")
    fun obtenerIdVoluntario(@Query("nombre") nombre: String): Call<Int>

    @Headers("Content-Type: application/json")
    @POST("agregarPersonal")
    fun postPersonal(@Body iSData: PersonalData): Call<Void>
}