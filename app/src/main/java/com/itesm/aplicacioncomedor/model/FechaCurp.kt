package com.itesm.aplicacioncomedor.model

import android.icu.text.SimpleDateFormat
import com.itesm.aplicacioncomedor.model.asistencia.AsistentesData
import java.util.Calendar
import java.util.Locale

/*
*   Cuando el CURP está siendo escrito, se crea la fecha en un formato en específico
*  Ejemplo: RAME030103... = 2003-01-03
*/
class FechaCurp {
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