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

class AsistenciaVM : ViewModel() {

    // LiveData
    val listaAsistente = MutableLiveData<List<AsistentesData>>()
    val idAsistente = MutableLiveData<Int>()
    val exitoso = MutableLiveData<Boolean>()
    val conexionExitosa = MutableLiveData<Boolean>()
    val asistenteEncontrado = MutableLiveData<Boolean>()


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
    fun registrarAsistentes () {

        // Borrar cuando ya haya servidor
        // Inicio Borrado
        val listSustituto: List<AsistentesData> = listOf(
        AsistentesData("Anonimo", "2000-02-18"),
        AsistentesData("Anonimo", "2022-04-14"),
        AsistentesData("Anonimo", "1990-23-40"),
        AsistentesData("Anonimo", "1959-04-23"),
        AsistentesData("Anonimo", "1947-07-24")
        )
        // Final Borrado

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
                    listaAsistente.value = listSustituto
                }
            }

            override fun onFailure(call: Call<List<AsistentesData>>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                listaAsistente.value = listSustituto
            }

        })
    }

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
                    println("FALLA: ${response.errorBody()}")
                    asistenteEncontrado.value = false
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }

        })
    }

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