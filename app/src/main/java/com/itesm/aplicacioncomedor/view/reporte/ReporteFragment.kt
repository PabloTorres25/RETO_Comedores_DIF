package com.itesm.aplicacioncomedor.view.reporte

import android.app.AlertDialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.itesm.aplicacioncomedor.databinding.FragmentReportaProblemaBinding
import com.itesm.aplicacioncomedor.viewmodel.ReporteVM
import com.itesm.aplicacioncomedor.viewmodel.SharedVM
import java.util.Date
import java.util.Locale
import android.icu.util.TimeZone
import androidx.lifecycle.Observer

class ReporteFragment : Fragment() {

    private lateinit var binding: FragmentReportaProblemaBinding

    private val vmShared: SharedVM by activityViewModels()
    private val vmReporte: ReporteVM by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentReportaProblemaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarNombre()
        registrarEventos()
        registrarObservadores()
    }

    private fun registrarObservadores() {
        // Saber si la respuesta fue exitosa o una falla
        vmReporte.exitosoApi.observe(viewLifecycleOwner, Observer {exitoso ->
            if(exitoso){
                binding.etDescripcion.text.clear()
                mostrarDialogoExitoso("Aviso Enviado")
            }
            else{
                mostrarDialogo("No se pudo registrar el Voluntario, " +
                        "comprueba tu conexión a internet.")
            }
        })
    }


    // Se busca el id del comedor con el que se ingresó se coloca en un EditText
    private fun registrarNombre() {
        val nombreComedor = vmShared.nombreComedorSH.value
        binding.etIdComedor.text = nombreComedor.toString().toEditable()
    }
    // Se obtiene la hora actual
    private fun obtenerFechaHoraActual(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("America/Mexico_City")
        return sdf.format(Date())
    }

    private fun registrarEventos() {
        // Diccionario con los tipos de problemas
        val diccionarioTipos = mutableMapOf(
            "Falta de servicios" to 1,
            "Comedor cerrado hoy" to 2,
            "Falta de alimentos" to 3,
            "Fallas de equipo de trabajo" to 4,
            "Falta de personal" to 5,
            "Otro" to 6)

        binding.btnReporte.setOnClickListener{
            val numeroComedor = vmShared.idComedorSH.value
            val descripcion = binding.etDescripcion.text.toString()
            val fecha = obtenerFechaHoraActual()
            val claveSeleccionada = binding.spTipoReporte.selectedItem.toString()
            val valorSeleccionado = diccionarioTipos[claveSeleccionada]
            if (descripcion.isNotEmpty()) {
                if (valorSeleccionado != null) {
                    vmReporte.enviarAviso(numeroComedor.toString(),valorSeleccionado, fecha, descripcion, )
                } else {
                    mostrarDialogo("Comprueba tu conexión.")
                }
            } else{
                mostrarDialogo("El campo de descripción esta vacío.")
            }
        }
    }

    // En caso de error se muestra este diálogo
    private fun mostrarDialogo(contenido: String){
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(contenido)
            .setTitle("Error")
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }

    // En caso de éxito se muestra este diálogo
    private fun mostrarDialogoExitoso(contenido: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(contenido)
            .setTitle("Éxito")
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }

    private fun String.toEditable(): Editable {
        return Editable.Factory.getInstance().newEditable(this)
    }

}
