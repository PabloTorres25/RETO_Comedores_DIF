package com.itesm.aplicacioncomedor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itesm.aplicacioncomedor.model.registro_nuevo.CondicionData
import com.itesm.aplicacioncomedor.model.registro_nuevo.RegistroApi
import com.itesm.aplicacioncomedor.model.registro_nuevo.RegistroDataClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/*
* Aquí es donde se leen las respuestas de las apis consultadas.
*/
class RegistroNuevoVM : ViewModel()
{
    // Live data
    val exitosoPost = MutableLiveData<Boolean>()
    val conexionExitosa = MutableLiveData<Boolean>()
    val exitosoCondicion = MutableLiveData<Boolean>()

    // El objeto retrofit
    private val retrofitIS by lazy {
        Retrofit.Builder()
            .baseUrl("https://comedores-dif.serveftp.com:443/")  // Servidor remoto
            .addConverterFactory(GsonConverterFactory.create())  // JSON
            .build()
    }
    // Crea el objeto que instancia al objeto que hará la descarga de datos
    private val descargaAPI by lazy {
        retrofitIS.create(RegistroApi::class.java)
    }

    // Se registra un nuevo Beneficiario
    fun registrarBeneficiario( nombreBenef: String, curp: String, fecha: String,
                               sexo: String, calle: String, colonia: String, municipio: String){
        val servicio = RegistroDataClass(nombreBenef, curp,
            fecha, sexo, calle, colonia, municipio)
        val call = descargaAPI.agregarBeneficiario(servicio)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>){
                if (response.isSuccessful) {
                    // Solicitud POST exitosa, sin respuesta JSON
                    println("POST exitoso")
                    exitosoPost.value = true
                } else {
                    // Manejar respuesta no exitosa
                    println("Solicitud POST no exitosa")
                    exitosoPost.value = false
                }
                conexionExitosa.value = true        // Si hay conexión
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                conexionExitosa.value = false               // No hay conexión
            }
        })
    }

    // Se registra una condición en caso de tenerla
    fun registrarCondicion(idBeneficiario: Int, idCondicion: Int){
        val servicio = CondicionData(idBeneficiario, idCondicion)
        val call = descargaAPI.agregarCondicion(servicio)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>){
                if (response.isSuccessful) {
                    // Solicitud POST exitosa, sin respuesta JSON
                    println("POST CONDICIÓN exitoso")
                    exitosoCondicion.value = true
                } else {
                    // Manejar respuesta no exitosa
                    println("Solicitud POST CONDICIÓN no exitosa")
                    exitosoCondicion.value = false
                }
                conexionExitosa.value = true        // Si hay conexión a la base de datos
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                conexionExitosa.value = false       // No hay conexión a la base de datos
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }
}