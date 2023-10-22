package com.itesm.aplicacioncomedor.view.familia

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.model.asistencia.AsistentesData
import com.itesm.aplicacioncomedor.viewmodel.FamiliaViewModel
import java.util.Calendar
import java.util.Locale

class AdaptadorFamilia(private val contexto: Context,
                       private var arrAsistentes: MutableList<AsistentesData>,
                       private val familiaViewModel: FamiliaViewModel)
    : RecyclerView.Adapter<AdaptadorFamilia.RenglonAsistente>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenglonAsistente {
        val vista = LayoutInflater.from(contexto).inflate(
            R.layout.familiares,
            parent, false)
        return RenglonAsistente(vista)
    }

    override fun onBindViewHolder(holder: RenglonAsistente, position: Int) {
        val asistentesData = arrAsistentes[position]
        holder.set(asistentesData)

        val ibEliminarFamiliar = holder.vistaRenglon.findViewById<ImageButton>(R.id.ibEliminarFamiliar)
        // Se elimina un beneficiario de la familia si así se desea
        ibEliminarFamiliar.setOnClickListener {
            familiaViewModel.arrFamilia.remove(asistentesData)
            notifyItemRemoved(position)
        }
    }

    // El número datos (items) del recyclerView
    override fun getItemCount(): Int {
        return arrAsistentes.size
    }


    class RenglonAsistente(var vistaRenglon: View) : RecyclerView.ViewHolder(vistaRenglon) {

        fun set(asistente: AsistentesData) {
            val fecha = asistente.fechaNacimiento.substring(0, 10)

            val formato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val fechaNacimientoDate = formato.parse(fecha)

            // Obtener la fecha actual
            val calendarHoy = Calendar.getInstance()
            val calendarNacimiento = Calendar.getInstance()
            calendarNacimiento.time = fechaNacimientoDate

            var edad = calendarHoy.get(Calendar.YEAR) - calendarNacimiento.get(Calendar.YEAR)

            // Ajustar la edad si aún no ha cumplido años en este año
            if (calendarHoy.get(Calendar.DAY_OF_YEAR) < calendarNacimiento.get(Calendar.DAY_OF_YEAR)) {
                edad--
            }
            vistaRenglon.findViewById<TextView>(R.id.tvNombreFamiliar).text = asistente.nombre
            vistaRenglon.findViewById<TextView>(R.id.tvEdadFamiliar).text = edad.toString()
        }
    }
}
