package com.itesm.aplicacioncomedor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itesm.aplicacioncomedor.model.voluntario.PersonalData
import com.itesm.aplicacioncomedor.model.voluntario.VoluntarioApi
import com.itesm.aplicacioncomedor.model.voluntario.VoluntarioData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VoluntarioVM : ViewModel()
{
    val exitoso = MutableLiveData<Boolean>()
    val conexionExitosa = MutableLiveData<Boolean>()
    val voluntarioEncontrado = MutableLiveData<Boolean>()
    val idVoluntario = MutableLiveData<Int>()

    private val retrofitIS by lazy {
        Retrofit.Builder()
            .baseUrl("http://44.220.12.52:8080/")  // Servidor remoto
            .addConverterFactory(GsonConverterFactory.create())  // JSON
            .build()
    }
    // Crea el objeto que instancia al objeto que hará la descarga de datos
    private val descargaAPI by lazy {
        retrofitIS.create(VoluntarioApi::class.java)
    }
    fun enviarVoluntario(nombre: String, fechaNacimiento: String, telefono: String){
        val servicio = VoluntarioData(nombre, fechaNacimiento, telefono)
        val call = descargaAPI.postVoluntario(servicio)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>){
                if (response.isSuccessful) {
                    // Solicitud POST exitosa, sin respuesta JSON
                    exitoso.value = true
                    println("POST exitoso")
                } else {
                    // Manejar respuesta no exitosa
                    exitoso.value = false
                    println("Solicitud POST no exitosa")
                    println("${nombre} + ${fechaNacimiento}+ ${telefono}")
                }
                conexionExitosa.value = true        // Si hay conexióna  la base de datos
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                conexionExitosa.value = false       // No hay conexióna  la base de datos
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    fun obtenerIdVol(nombreComedor: String) {
        val call = descargaAPI.obtenerIdVoluntario(nombreComedor)
        call.enqueue(object: Callback<Int> {
            override fun onResponse(call: Call<Int>,
                                    response: Response<Int>
            ) {
                if (response.isSuccessful) {
                    println("RESPUESTA: ${response.body()}")
                    voluntarioEncontrado.value = true
                    idVoluntario.value = response.body()
                } else {
                    println("FALLA: ${response.errorBody()}")
                    voluntarioEncontrado.value = false
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }

        })
    }

    fun enviarPersonal(comedor: Int, voluntarioId: Int, rol: String){
        val servicio = PersonalData(comedor, voluntarioId, rol)
        val call = descargaAPI.postPersonal(servicio)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>){
                if (response.isSuccessful) {
                    // Solicitud POST exitosa, sin respuesta JSON
                    exitoso.value = true
                    println("POST exitoso")
                } else {
                    // Manejar respuesta no exitosa
                    exitoso.value = false
                    println("Solicitud POST no exitosa")
                    println("${comedor} + ${voluntarioId}+ ${rol}")
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