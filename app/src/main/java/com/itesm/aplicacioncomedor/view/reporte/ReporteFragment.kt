package com.itesm.aplicacioncomedor.view.reporte

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
import com.itesm.aplicacioncomedor.viewmodel.IniciarSesionVM
import com.itesm.aplicacioncomedor.viewmodel.ReporteVM
import com.itesm.aplicacioncomedor.viewmodel.SharedVM
import java.util.Date
import java.util.Locale
import java.util.TimeZone.getTimeZone
import android.icu.util.TimeZone

class ReporteFragment : Fragment() {

    private lateinit var binding: FragmentReportaProblemaBinding

    private val vmShared: SharedVM by activityViewModels()
    private val vmReporte: ReporteVM by viewModels()

    val currentDate = Date()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentReportaProblemaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nombreComedor = vmShared.nombreComedorSH.value
        binding.etIdComedor.text = nombreComedor.toString().toEditable()
        registrarEventos()
    }
    fun obtenerFechaHoraActual(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("America/Mexico_City")
        return sdf.format(Date())
    }

    private fun registrarEventos() {
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
            if (valorSeleccionado != null) {
                vmReporte.enviarAviso(numeroComedor.toString(),valorSeleccionado, fecha, descripcion, )
            }
        }
    }

    fun String.toEditable(): Editable {
        return Editable.Factory.getInstance().newEditable(this)
    }

}
