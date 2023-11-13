package com.itesm.aplicacioncomedor.view.asistencia

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.CheckBox
import android.widget.Spinner
import androidx.core.content.ContextCompat
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

        // Diálogo que se muestra al darle clic a un beneficiario
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
            val Edad = fechaaEdad.fechanacimientoaEdad(asistentesData)
            val mensaje:String = if(Edad == 1){
                "${asistentesData.nombre}, ${Edad} año \n"

            } else{
                "${asistentesData.nombre}, ${Edad} años \n"
            }
            tvAsistenteDialog.text = mensaje

            val positiveButtonText = "Aceptar"
            val spannablePositiveButton = SpannableString(positiveButtonText)
            val colorButton = ContextCompat.getColor(contexto, R.color.color_botones)

            spannablePositiveButton.setSpan(ForegroundColorSpan(colorButton), 0, positiveButtonText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            dialog.setPositiveButton(spannablePositiveButton) { _, _ ->
                // Si le da Aceptar hacer POST del Registro
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

            val negativeButtonText = "Cancelar"
            val spannableNegativeButton = SpannableString(negativeButtonText)

            spannableNegativeButton.setSpan(ForegroundColorSpan(colorButton), 0, negativeButtonText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            dialog.setNegativeButton(spannableNegativeButton) { _, _ ->
                println("Se cerro el Dialog")
            }
            dialog.show()
        }

    }

    // Se le notifica al adaptador si hubo un cambio
    @SuppressLint("NotifyDataSetChanged")
    fun actualizarArreglo(arrAsistentes: Array<AsistentesData>) {
        this.arrAsistentes = arrAsistentes
        notifyDataSetChanged()
    }


    class RenglonAsistente(private var vistaRenglon: View) : RecyclerView.ViewHolder(vistaRenglon) {
        fun set(asistente: AsistentesData) {
            // Se calcula la edad basándose en la fecha de nacimiento
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
