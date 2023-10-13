package com.itesm.aplicacioncomedor.view.asistencia

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.model.asistencia.Asistentes
import com.itesm.aplicacioncomedor.model.asistencia.AsistentesData
import java.util.Calendar
import java.util.Locale

class AdaptadorRegistrosFamilia (private val contexto: Context,
                                 var arrAsistentes: Array<AsistentesData>,
                                 private val elementosSeleccionados: MutableList<AsistentesData>,
                                 private val recyclerView: RecyclerView)
    : RecyclerView.Adapter<AdaptadorRegistrosFamilia.RenglonAsistente>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenglonAsistente {
        val vista = LayoutInflater.from(contexto).inflate(
            R.layout.familiares_registrados,
            parent, false)
        return RenglonAsistente(vista)
    }

    // El número datos (items) del recyclerView
    override fun getItemCount(): Int {
        return arrAsistentes.size
    }

    override fun onBindViewHolder(holder: RenglonAsistente, position: Int) {
        val asistentesData = arrAsistentes[position]
        holder.set(asistentesData)

        val checkBox = holder.vistaRenglon.findViewById<CheckBox>(R.id.cbAsistentes)

        checkBox.isChecked = elementosSeleccionados.contains(asistentesData)

        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // Agrega el elemento a la lista de elementos seleccionados
                elementosSeleccionados.add(asistentesData)
            } else {
                // Remueve el elemento de la lista de elementos seleccionados
                elementosSeleccionados.remove(asistentesData)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun actualizarArreglo(arrAsistentes: Array<AsistentesData>) {
        val elementosSeleccionadosAntesDeActualizar = ArrayList(elementosSeleccionados)

        this.arrAsistentes = arrAsistentes
        notifyDataSetChanged()
        elementosSeleccionados.clear()
        elementosSeleccionados.addAll(elementosSeleccionadosAntesDeActualizar)

        configurarCheckBoxes()
    }

    fun configurarCheckBoxes() {
        for (i in 0 until itemCount) {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(i)
            val checkBox = viewHolder?.itemView?.findViewById<CheckBox>(R.id.cbAsistentes)
            val asistente = arrAsistentes[i]
            checkBox?.isChecked = elementosSeleccionados.contains(asistente)
        }
    }

    class RenglonAsistente(var vistaRenglon: View) : RecyclerView.ViewHolder(vistaRenglon) {

        fun set(asistente: AsistentesData) {
            val fecha = asistente.fechaNacimiento.substring(0,10)

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
            vistaRenglon.findViewById<TextView>(R.id.tvNombreRegistrado).text = asistente.nombre
            vistaRenglon.findViewById<TextView>(R.id.tvEdadRegistrado).text = edad.toString()
        }
    }
}