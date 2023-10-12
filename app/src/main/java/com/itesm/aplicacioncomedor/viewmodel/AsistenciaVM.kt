package com.itesm.aplicacioncomedor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itesm.aplicacioncomedor.model.asistencia.AsistenteApi
import com.itesm.aplicacioncomedor.model.asistencia.AsistentesData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AsistenciaVM : ViewModel() {

    // LiveData
    val listaAsistente = MutableLiveData<List<AsistentesData>>()

    private val retrofitIS by lazy {
        Retrofit.Builder()
            .baseUrl("http://44.220.12.52:8080/")  // Servidor remoto
            .addConverterFactory(GsonConverterFactory.create())  // JSON
            .build()
    }
    // Crea el objeto que instancia al objeto que hará la descarga de datos
    private val descargaAPI by lazy {
        retrofitIS.create(AsistenteApi::class.java)
    }
    fun registrarAsistentes () {
        val call = descargaAPI.obtenerAsistentes()
        call.enqueue(object: Callback<List<AsistentesData>> {
            override fun onResponse(call: Call<List<AsistentesData>>,
                                    response: Response<List<AsistentesData>>
            ) {
                if (response.isSuccessful) {
                    println("RESPUESTA: ${response.body()}")
                    listaAsistente.value = response.body()
                } else {
                    println("FALLA: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<AsistentesData>>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }

        })
    }
}