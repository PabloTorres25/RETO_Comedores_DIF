package com.itesm.aplicacioncomedor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itesm.aplicacioncomedor.model.iniciar_sesion.IniciarSesionApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SharedVM: ViewModel()
{
    val nombreComedorSH = MutableLiveData<String>()
    val idComedorSH = MutableLiveData<Int>()
    private val retrofitIS by lazy {
        Retrofit.Builder()
            .baseUrl("https://comedores-dif.serveftp.com:443/")
            // Servidor remoto
            .addConverterFactory(GsonConverterFactory.create())  // JSON
            .build()
    }
    // Crea el objeto que instancia al objeto que hará la descarga de datos
    private val descargaAPI by lazy {
        retrofitIS.create(IniciarSesionApi::class.java)
    }

    fun obtenerIdComedor (nombreComedor: String) {
        val call = descargaAPI.obtenerIdComedor(nombreComedor)
        call.enqueue(object: Callback<Int> {
            override fun onResponse(call: Call<Int>,
                                    response: Response<Int>
            ) {
                if (response.isSuccessful) {
                    println("RESPUESTA: ${response.body()}")
                    idComedorSH.value = response.body()
                } else {
                    println("FALLA: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }

        })
    }
}