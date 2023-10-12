package com.itesm.aplicacioncomedor.model.asistencia

import retrofit2.Call
import retrofit2.http.GET

interface AsistenteApi {
    @GET("obtenerBeneficiarios")
    fun obtenerAsistentes(): Call<List<AsistentesData>>
}