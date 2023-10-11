package com.itesm.aplicacioncomedor.model.asistencia

import retrofit2.Call
import retrofit2.http.GET

interface AsistenteApi {
    @GET("obtenerIdBeneficiario")
    fun obtenerAsistentes(): Call<List<AsistentesData>>
}