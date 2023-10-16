package com.itesm.aplicacioncomedor.view.reporte

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.itesm.aplicacioncomedor.databinding.FragmentReportaProblemaBinding
import com.itesm.aplicacioncomedor.viewmodel.IniciarSesionVM
import com.itesm.aplicacioncomedor.viewmodel.SharedVM

class ReporteFragment : Fragment() {

    private lateinit var binding: FragmentReportaProblemaBinding

    private val vmShared: SharedVM by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentReportaProblemaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nombreComedor = vmShared.nombreComedorSH.value
        val numeroComedor = vmShared.idComedorSH.value
        println("Este print es de reporta ${numeroComedor}")
        binding.etIdComedor.text = nombreComedor.toString().toEditable()
    }

    fun String.toEditable(): Editable {
        return Editable.Factory.getInstance().newEditable(this)
    }

}
