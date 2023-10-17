package com.itesm.aplicacioncomedor.view.asistencia

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.CheckBox
import android.widget.Spinner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.model.FechaCurp
import com.itesm.aplicacioncomedor.model.asistencia.AsistentesData
import java.util.Calendar
import java.util.Locale

class AdaptadorAsistentes(private val contexto: Context,
                          var arrAsistentes: Array<AsistentesData>,
                          var fechaaEdad: FechaCurp)
    : RecyclerView.Adapter<AdaptadorAsistentes.RenglonAsistente>() {

    val btnAceptar = MutableLiveData<Boolean>()
    val nombreBenef = MutableLiveData<String>()
    val fechaBenef = MutableLiveData<String>()
    val presente = MutableLiveData<String>()
    val pagado = MutableLiveData<String>()

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
            val inflater = LayoutInflater.from(holder.itemView.context)
            val dialogView = inflater.inflate(R.layout.dialog_layout, null)
            dialog.setView(dialogView)

            val tvAsistenteDialog = dialogView.findViewById<TextView>(R.id.tvAsistenteDialog)
            val cbPresente = dialogView.findViewById<CheckBox>(R.id.cbPresente)

            val spPagadoDonado = dialogView.findViewById<Spinner>(R.id.spPagadoDonado)
            val types = holder.itemView.context.resources.getStringArray(R.array.tipos_asistencia)
            val adapterAsistencia = ArrayAdapter(holder.itemView.context, R.layout.dropdown_item, types)
            spPagadoDonado.adapter = adapterAsistencia

            // Nombre y Edad
            var Edad = fechaaEdad.fechanacimientoaEdad(asistentesData)
            var mensaje:String
            if(Edad == 1){
                mensaje = "${asistentesData.nombre}, ${Edad} año \n"

            } else{
                mensaje = "${asistentesData.nombre}, ${Edad} años \n"
            }
            tvAsistenteDialog.setText(mensaje)

            dialog.setPositiveButton("Aceptar") { _, _ ->
                // Si le da Aceptar hacer POST de el Registro
                nombreBenef.value = asistentesData.nombre
                fechaBenef.value = asistentesData.fechaNacimiento.substring(0,10)
                println(nombreBenef.value)
                println(fechaBenef.value)
                // Para mandar el checkbox
                if (cbPresente.isChecked) {
                    presente.value = "1"
                    // El CheckBox está marcado
                } else {
                    // El CheckBox no está marcado
                    presente.value = "0"
                }
                //Para mandar el spiner
                val selectedOption = spPagadoDonado.selectedItem.toString()
                if (selectedOption == "Pagado $13") {
                    pagado.value = "1"
                    // El CheckBox está marcado
                } else {
                    // El CheckBox no está marcado
                    pagado.value = "0"
                }
                btnAceptar.value = true
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
