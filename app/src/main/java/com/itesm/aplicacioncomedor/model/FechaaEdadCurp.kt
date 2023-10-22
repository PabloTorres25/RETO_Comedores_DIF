package com.itesm.aplicacioncomedor.model


/*
*  Calcula la edad basándose en la fecha de nacimiento
*/
class FechaaEdadCurp {
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