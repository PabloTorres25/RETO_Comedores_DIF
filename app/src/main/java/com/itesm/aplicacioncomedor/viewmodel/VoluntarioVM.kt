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
    val exitosoPersonal = MutableLiveData<Boolean>()
    val conexionExitosa = MutableLiveData<Boolean>()
    val voluntarioEncontrado = MutableLiveData<Boolean>()
    val idVoluntario = MutableLiveData<Int>()


    private val retrofitIS by lazy {
        Retrofit.Builder()
            .baseUrl("https://comedores-dif.serveftp.com:443/")  // Servidor remoto
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
                    println("POST Voluntario exitoso")
                    exitoso.value = true
                } else {
                    // Manejar respuesta no exitosa
                    exitoso.value = false
                    println("Solicitud POST no exitosa")
                }
                conexionExitosa.value = true        // Si hay conexióna  la base de datos
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                conexionExitosa.value = false       // No hay conexióna  la base de datos
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    fun obtenerIdVol(nombreVoluntario: String) {
        val call = descargaAPI.obtenerIdVoluntario(nombreVoluntario)
        call.enqueue(object: Callback<Int> {
            override fun onResponse(call: Call<Int>,
                                    response: Response<Int>
            ) {
                if (response.isSuccessful) {
                    println("RESPUESTA Getid: ${response.body()}")
                    idVoluntario.value = response.body()
                    println("idVoluntario respuesta: ${idVoluntario.value}")
                    voluntarioEncontrado.value = true
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
        println("Esto es una prueba si sale en medio")
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>){
                if (response.isSuccessful) {
                    // Solicitud POST exitosa, sin respuesta JSON
                    exitosoPersonal.value = true
                    println("POST Personal exitoso")
                } else {
                    // Manejar respuesta no exitosa
                    exitosoPersonal.value = false
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