package com.itesm.aplicacioncomedor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itesm.aplicacioncomedor.model.asistencia.AsistentesData
import com.itesm.aplicacioncomedor.model.familia.FamiliaApi
import com.itesm.aplicacioncomedor.model.familia.FamiliaCreadaData
import com.itesm.aplicacioncomedor.model.familia.FamiliaData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/*
* Aquí es donde se leen las respuestas de las apis consultadas.
*/
class FamiliaViewModel : ViewModel() {

    // Live data
    val arrFamilia: MutableList<AsistentesData> = mutableListOf()
    val exitosoFamilia = MutableLiveData<Boolean>()
    val conexionExitosa = MutableLiveData<Boolean>()
    val exitosaFamilia = MutableLiveData<Boolean>()
    val idFamilia = MutableLiveData<Int>()
    val familiaEncontrada = MutableLiveData<Boolean>()

    // El objeto retrofit
    private val retrofitIS by lazy {
        Retrofit.Builder()
            .baseUrl("https://comedores-dif.serveftp.com:443/")  // Servidor remoto
            .addConverterFactory(GsonConverterFactory.create())  // JSON
            .build()
    }
    // Crea el objeto que instancia al objeto que hará la descarga de datos
    private val descargaAPI by lazy {
        retrofitIS.create(FamiliaApi::class.java)
    }

    // Se crea una familia con el nombre dado
    fun crearFamilia(nombre: String){
        val servicio = FamiliaCreadaData(nombre)
        val call = descargaAPI.registrarFamilia(servicio)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>){
                if (response.isSuccessful) {
                    // Solicitud POST exitosa, sin respuesta JSON
                    println("FAMILIA CREADA")
                    exitosoFamilia.value = true
                } else {
                    // Manejar respuesta no exitosa
                    exitosoFamilia.value = false
                    println("FAMILIA NO CREADA")
                }
                conexionExitosa.value = true        // Si hay conexión a la base de datos
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                conexionExitosa.value = false       // No hay conexión a la base de datos
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }


    // Se registra un beneficiario a una familia
    fun regitrarBenefFam(idFamilia: Int, idBeneficiario: Int){
        val servicio = FamiliaData(idFamilia, idBeneficiario)
        val call = descargaAPI.agregarFamiliaBeneficiario(servicio)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>){
                if (response.isSuccessful) {
                    // Solicitud POST exitosa, sin respuesta JSON
                    println("POST CONDICION exitoso")
                    exitosaFamilia.value = true
                } else {
                    // Manejar respuesta no exitosa
                    println("Solicitud POST CONDICION no exitosa")
                    exitosaFamilia.value = false
                }
                conexionExitosa.value = true        // Si hay conexión a la base de datos
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                conexionExitosa.value = false       // No hay conexión a la base de datos
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    // Se obtiene el ID de una familia
    fun obtenerIdFamilia(nombre: String) {
        val call = descargaAPI.obtenerIdFamilia(nombre)
        call.enqueue(object: Callback<Int> {
            override fun onResponse(call: Call<Int>,
                                    response: Response<Int>
            ) {
                if (response.isSuccessful) {
                    idFamilia.value = response.body()
                    println("FAMILIA respuesta: ${idFamilia.value}")
                    familiaEncontrada.value = true
                } else {
                    println("FALLA: ${response.errorBody()}")
                    familiaEncontrada.value = false
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                conexionExitosa.value = false
                println("ERROR: ${t.localizedMessage}")
            }

        })
    }
}