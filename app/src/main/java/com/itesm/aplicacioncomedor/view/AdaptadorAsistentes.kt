package com.itesm.aplicacioncomedor.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.model.Asistentes

class AdaptadorAsistentes (private val contexto: Context, var arrAsistentes: Array<Asistentes>)
    : RecyclerView.Adapter<AdaptadorAsistentes.RenglonAsistente>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenglonAsistente {
        val vista = LayoutInflater.from(contexto).inflate(R.layout.asistentes,
            parent, false)
        return RenglonAsistente(vista)
    }

    // El número datos (items) del recyclerView
    override fun getItemCount(): Int {
        return arrAsistentes.size
    }

    override fun onBindViewHolder(holder: RenglonAsistente, position: Int) {
        val asistente = arrAsistentes[position]
        holder.set(asistente)
    }

    class RenglonAsistente (var vistaRenglon: View) : RecyclerView.ViewHolder(vistaRenglon)
    {
        fun set(asistente: Asistentes) {
            vistaRenglon.findViewById<TextView>(R.id.tvNombreAsistentes).text = asistente.nombre
            vistaRenglon.findViewById<TextView>(R.id.tvEdadAsistentes).text = asistente.edad.toString()
        }
    }
}
