package com.itesm.aplicacioncomedor.model

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class FechaaEdadCurp {
    @SuppressLint("SimpleDateFormat")
    fun fechanacimientoaEdad(curp: String): Int {
        val fechaNacimiento = convertirFormatoFechaYYToYYYY(curp)

        val formato = SimpleDateFormat("yyMMdd")
        val fechaNac = formato.parse(fechaNacimiento)
        val fechaActual = Date()

        val calendarNac = Calendar.getInstance()
        calendarNac.time = fechaNac

        val calendarActual = Calendar.getInstance()
        calendarActual.time = fechaActual

        var edad = calendarActual.get(Calendar.YEAR) - calendarNac.get(Calendar.YEAR)

        // Comprueba si la fecha actual es anterior al cumpleaños de la persona en este año
        if (calendarActual.get(Calendar.DAY_OF_YEAR) < calendarNac.get(Calendar.DAY_OF_YEAR)) {
            edad--
        }
        println("Edad = ${edad}")
        return edad
    }
    fun convertirFormatoFechaYYToYYYY(curp: String): String {
        val yy = curp.substring(4, 6)
        val mm = curp.substring(6, 8)
        val dd = curp.substring(8, 10)

        val year = if (yy.toInt() < 99) {
            "20$yy"
        } else {
            "19$yy"
        }

        return "$year-$mm-$dd"
    }

}