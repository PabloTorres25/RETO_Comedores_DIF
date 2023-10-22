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
/*
* Aquí es donde se leen las respuestas de las apis consultadas.
*/
class IniciarSesionVM : ViewModel()
{
    // Live data
    val autenticacionExitosa = MutableLiveData<Boolean>()
    val conexionExitosa = MutableLiveData<Boolean>()

    // El objeto retrofit
    private val retrofitIS by lazy {
        Retrofit.Builder()
            .baseUrl("https://comedores-dif.serveftp.com:443/")  // Servidor remoto
            .addConverterFactory(GsonConverterFactory.create())  // JSON
            .build()
    }
    // Crea el objeto que instancia al objeto que hará la descarga de datos
    private val descargaAPI by lazy {
        retrofitIS.create(IniciarSesionApi::class.java)
    }

    // Se verifica si el usuario y contraseña son correctos.
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


