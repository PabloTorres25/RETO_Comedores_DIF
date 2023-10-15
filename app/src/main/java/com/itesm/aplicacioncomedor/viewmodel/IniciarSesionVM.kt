package com.itesm.aplicacioncomedor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itesm.aplicacioncomedor.model.iniciar_sesion.IniciarSesionApi
import com.itesm.aplicacioncomedor.model.iniciar_sesion.IniciarSesionData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IniciarSesionVM : ViewModel()
{
    val autenticacionExitosa = MutableLiveData<Boolean>()
    val conexionExitosa = MutableLiveData<Boolean>()

    private val retrofitIS by lazy {
        Retrofit.Builder()
            .baseUrl("http://44.220.12.52:8080/")  // Servidor remoto
            .addConverterFactory(GsonConverterFactory.create())  // JSON
            .build()
    }
    // Crea el objeto que instancia al objeto que hará la descarga de datos
    private val descargaAPI by lazy {
        retrofitIS.create(IniciarSesionApi::class.java)
    }

    fun autentificaUsuario(usuario: String, contrasena: String){
        val servicio = IniciarSesionData(usuario, contrasena)
        val call = descargaAPI.autentificaUsuario(servicio)
        call.enqueue(object : Callback<Void> { // Cambia IniciarSesionData a Void
            override fun onResponse(call: Call<Void>, response: Response<Void>){
                if (response.isSuccessful) {
                    // Solicitud POST exitosa, sin respuesta JSON
                    autenticacionExitosa.value = true
                    println("POST exitoso")
                } else {
                    // Manejar respuesta no exitosa
                    autenticacionExitosa.value = false
                    println("Solicitud POST no exitosa")
                    println("${usuario} + ${contrasena}")
                }
                conexionExitosa.value = true        // Si hay conexióna  la base de datos
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                conexionExitosa.value = false       // No hay conexióna  la base de datos
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    /*
    fun prueba () {
        val call = descargaAPI.pruebaBeneficiarios()
        call.enqueue(object: Callback<Prueba2Beneficiarios> {
            override fun onResponse(call: Call<Prueba2Beneficiarios>,
                                    response: Response<Prueba2Beneficiarios>) {
                if (response.isSuccessful) {
                    println("RESPUESTA: ${response.body()}")
                } else {
                    println("FALLA: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Prueba2Beneficiarios>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }

        })
    }
    */
}


