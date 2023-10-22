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
/*
* Aquí es donde se leen las respuestas de las apis consultadas.
*/
class VoluntarioVM : ViewModel()
{
    // Live data
    val exitoso = MutableLiveData<Boolean>()
    val exitosoApiPersonal = MutableLiveData<Boolean>()
    val conexionExitosa = MutableLiveData<Boolean>()
    val voluntarioEncontrado = MutableLiveData<Boolean>()
    val idVoluntario = MutableLiveData<Int>()

    // El objeto retrofit
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
    // Se registra un voluntario
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
                conexionExitosa.value = true        // Si hay conexión a la base de datos
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                conexionExitosa.value = false       // No hay conexión a la base de datos
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    // Se obtiene el ID del voluntario
    fun obtenerIdVol(nombreVoluntario: String) {
        val call = descargaAPI.obtenerIdVoluntario(nombreVoluntario)
        call.enqueue(object: Callback<Int> {
            override fun onResponse(call: Call<Int>,
                                    response: Response<Int>
            ) {
                if (response.isSuccessful) {
                    println("RESPUESTA Exitosa: ${response.body()}")
                    idVoluntario.value = response.body()
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


    // Se registra el rol del voluntario
    fun enviarPersonal(comedor: Int, voluntarioId: Int, rol: String){
        val servicio = PersonalData(comedor, voluntarioId, rol)
        val call = descargaAPI.postPersonal(servicio)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>){
                if (response.isSuccessful) {
                    // Solicitud POST exitosa, sin respuesta JSON
                    exitosoApiPersonal.value = true
                    println("POST Personal exitoso")
                } else {
                    // Manejar respuesta no exitosa
                    exitosoApiPersonal.value = false
                    println("Solicitud POST no exitosa")
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }
}