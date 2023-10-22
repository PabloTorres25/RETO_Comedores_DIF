package com.itesm.aplicacioncomedor.model.reporte

import com.google.gson.annotations.SerializedName

/*
*   Datos que son pedidos dentro de "registrarAviso"
*   Representa los valores de un reporte
*/
data class ReporteData(
    @SerializedName("idComedor")
    var comedor: String,
    @SerializedName("idTipoAviso")
    var tipoAviso: Int,
    @SerializedName("fecha")
    var fecha: String,
    @SerializedName("descripcion")
    var descripcion: String
)
