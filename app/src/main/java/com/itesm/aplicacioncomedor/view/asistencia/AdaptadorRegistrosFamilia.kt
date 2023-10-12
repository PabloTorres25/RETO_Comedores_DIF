package com.itesm.aplicacioncomedor.view.asistencia

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.model.asistencia.Asistentes

class AdaptadorRegistrosFamilia (private val contexto: Context, var arrAsistentes: Array<Asistentes>,
                                private val onClickListener:(Asistentes) -> Unit)
    : RecyclerView.Adapter<AdaptadorRegistrosFamilia.RenglonAsistente>()
{
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
        val asistente = arrAsistentes[position]
        holder.set(asistente, onClickListener)
    }

    fun actualizarArreglo(arrAsistentes: Array<Asistentes>){
        this.arrAsistentes = arrAsistentes
        notifyDataSetChanged()
    }

    class RenglonAsistente (var vistaRenglon: View) : RecyclerView.ViewHolder(vistaRenglon)
    {
        fun set(asistente: Asistentes, onClickListener:(Asistentes) -> Unit) {
            vistaRenglon.findViewById<TextView>(R.id.tvNombreRegistrado).text = asistente.nombre
            vistaRenglon.findViewById<TextView>(R.id.tvEdadRegistrado).text = asistente.edad.toString()
            itemView.setOnClickListener{onClickListener(asistente)}
        }
    }
}