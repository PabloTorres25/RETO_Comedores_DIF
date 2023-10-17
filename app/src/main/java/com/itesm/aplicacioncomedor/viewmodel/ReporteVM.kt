package com.itesm.aplicacioncomedor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itesm.aplicacioncomedor.model.iniciar_sesion.IniciarSesionApi
import com.itesm.aplicacioncomedor.model.iniciar_sesion.IniciarSesionData
import com.itesm.aplicacioncomedor.model.reporte.ReporteApi
import com.itesm.aplicacioncomedor.model.reporte.ReporteData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReporteVM: ViewModel()
{
    val exitosoApi = MutableLiveData<Boolean>()
    val conexionExitosa = MutableLiveData<Boolean>()


    private val retrofitIS by lazy {
        Retrofit.Builder()
            .baseUrl("https://comedores-dif.serveftp.com:443/")  // Servidor remoto
            .addConverterFactory(GsonConverterFactory.create())  // JSON
            .build()
    }
    // Crea el objeto que instancia al objeto que hará la descarga de datos
    private val descargaAPI by lazy {
        retrofitIS.create(ReporteApi::class.java)
    }

    fun enviarAviso(comedor: String, tipoAviso: Int, fecha: String, descripcion: String){
        val servicio = ReporteData(comedor, tipoAviso, fecha, descripcion)
        val call = descargaAPI.postAviso(servicio)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>){
                if (response.isSuccessful) {
                    // Solicitud POST exitosa, sin respuesta JSON
                    exitosoApi.value = true
                    println("POST exitoso")
                } else {
                    // Manejar respuesta no exitosa
                    exitosoApi.value = false
                    println("Solicitud POST no exitosa")
                    println("${comedor} + ${fecha}+ ${descripcion}")
                }
                conexionExitosa.value = true        // Si hay conexióna  la base de datos
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                conexionExitosa.value = false       // No hay conexióna  la base de datos
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }
}