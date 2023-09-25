package com.itesm.aplicacioncomedor.ui.reporta_problema

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.itesm.aplicacioncomedor.databinding.FragmentInicioSesionBinding
import com.itesm.aplicacioncomedor.databinding.FragmentReportaProblemaBinding

class ReporteFragment : Fragment() {

    private lateinit var binding: FragmentReportaProblemaBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentReportaProblemaBinding.inflate(layoutInflater)
        return binding.root
    }

}