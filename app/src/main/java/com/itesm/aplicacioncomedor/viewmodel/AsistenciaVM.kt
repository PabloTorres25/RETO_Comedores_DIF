package com.itesm.aplicacioncomedor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itesm.aplicacioncomedor.model.asistencia.AsistenciaRegistroData
import com.itesm.aplicacioncomedor.model.asistencia.AsistenteApi
import com.itesm.aplicacioncomedor.model.asistencia.AsistentesData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/*
* Aquí es donde se leen las respuestas de las apis consultadas.
*/
class AsistenciaVM : ViewModel() {

    // LiveData
    val listaAsistente = MutableLiveData<List<AsistentesData>>()
    val idAsistente = MutableLiveData<Int>()
    val exitoso = MutableLiveData<Boolean>()
    val conexionExitosa = MutableLiveData<Boolean>()
    val asistenteEncontrado = MutableLiveData<Boolean>()

    // El objeto retrofit
    private val retrofitIS by lazy {
        Retrofit.Builder()
            .baseUrl("https://comedores-dif.serveftp.com:443/")  // Servidor remoto
            .addConverterFactory(GsonConverterFactory.create())  // JSON
            .build()
    }
    // Crea el objeto que instancia al objeto que hará la descarga de datos
    private val descargaAPI by lazy {
        retrofitIS.create(AsistenteApi::class.java)
    }
    // Se recibe una lista con todos los beneficiarios ya registrados
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

    // Se obtiene el ID de un beneficiario
    fun obtenerBeneficiario(nombreAsistente: String, fecha: String) {
        val call = descargaAPI.obtenerBeneficiario(nombreAsistente, fecha)
        call.enqueue(object: Callback<Int> {
            override fun onResponse(call: Call<Int>,
                                    response: Response<Int>
            ) {
                if (response.isSuccessful) {
                    idAsistente.value = response.body()
                    println("idVoluntario respuesta: ${idAsistente.value}")
                    asistenteEncontrado.value = true
                } else {
                    println("FALLA ID: ${response.errorBody()}")
                    asistenteEncontrado.value = false
                }
                conexionExitosa.value = true //Hay conexión a la BD
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                conexionExitosa.value = false               // No hay conexión a la BD
                println("ERROR: ${t.localizedMessage}")
            }

        })
    }

    // Se registra una Asistencia
    fun registrarAsistencia(idComedor: Int, idBeneficiario: Int, fecha: String,
                           comidaPagada: String, presente: String){
        val servicio = AsistenciaRegistroData(idComedor, idBeneficiario,
            fecha, comidaPagada, presente)
        val call = descargaAPI.registraAsistencia(servicio)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>){
                if (response.isSuccessful) {
                    // Solicitud POST exitosa, sin respuesta JSON
                    println("POST ASISTENCIA exitoso")
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
}