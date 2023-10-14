package com.itesm.aplicacioncomedor.model

import android.icu.text.SimpleDateFormat
import android.widget.TextView
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.model.asistencia.AsistentesData
import java.util.Calendar
import java.util.Locale

class FechaaEdad {
    fun fechanacimientoaEdad(asistente: AsistentesData): Int{
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
        return edad
    }
}