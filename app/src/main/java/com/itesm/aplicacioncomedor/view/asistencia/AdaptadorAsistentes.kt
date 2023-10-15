package com.itesm.aplicacioncomedor.view.asistencia

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.CheckBox
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.model.FechaaEdad
import com.itesm.aplicacioncomedor.model.asistencia.AsistentesData
import java.util.Calendar
import java.util.Locale

class AdaptadorAsistentes(private val contexto: Context,
                          var arrAsistentes: Array<AsistentesData>,
                          var fechaaEdad: FechaaEdad)
    : RecyclerView.Adapter<AdaptadorAsistentes.RenglonAsistente>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenglonAsistente {
        val vista = LayoutInflater.from(contexto).inflate(
            R.layout.asistentes,
            parent, false
        )
        return RenglonAsistente(vista)
    }

    // El número datos (items) del recyclerView
    override fun getItemCount(): Int {
        return arrAsistentes.size
    }

    override fun onBindViewHolder(holder: RenglonAsistente, position: Int) {
        val asistentesData = arrAsistentes[position]
        holder.set(asistentesData)

        holder.itemView.setOnClickListener {
            val dialog = AlertDialog.Builder(holder.itemView.context)
            dialog.setTitle("Pasar Asistencia")

            // Nombre y Edad
            var Edad = fechaaEdad.fechanacimientoaEdad(asistentesData)
            // Si se agrega CURP a asistentesData, llamarlo aqui y ponerlo dentro del mensaje
            if(Edad == 1){
                val mensaje = "${asistentesData.nombre}, ${Edad} año \n"
                dialog.setMessage(mensaje)
            } else{
                val mensaje = "${asistentesData.nombre}, ${Edad} años \n"
                dialog.setMessage(mensaje)
            }

            // Checkbox
            val presente = CheckBox(contexto)
            presente.text = "Presente"
            //dialog.setView(presente)

            dialog.setPositiveButton("Aceptar") { _, _ ->
                // Si le da Aceptar hacer POST de el Registro
                if (presente.isChecked) {
                    // El CheckBox está marcado
                } else {
                    // El CheckBox no está marcado
                }
            }

            dialog.setNegativeButton("Cancelar") { _, _ ->
                println("Se cerro el Dialog")
            }
            dialog.show()
        }

    }


    @SuppressLint("NotifyDataSetChanged")
    fun actualizarArreglo(arrAsistentes: Array<AsistentesData>) {
        this.arrAsistentes = arrAsistentes
        notifyDataSetChanged()
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
            vistaRenglon.findViewById<TextView>(R.id.tvNombreAsistentes).text = asistente.nombre
            vistaRenglon.findViewById<TextView>(R.id.tvEdadAsistentes).text = edad.toString()
        }
    }
}
