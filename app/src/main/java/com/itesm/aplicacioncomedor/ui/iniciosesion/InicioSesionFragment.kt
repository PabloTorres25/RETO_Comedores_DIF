package com.itesm.aplicacioncomedor.ui.iniciosesion

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.databinding.ActivityMainBinding
import com.itesm.aplicacioncomedor.databinding.FragmentAsistenciaBinding
import com.itesm.aplicacioncomedor.databinding.FragmentInicioSesionBinding
import com.itesm.aplicacioncomedor.ui.asistencia.AsistenciaFragment

class InicioSesionFragment : Fragment() {

    private lateinit var binding: FragmentInicioSesionBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInicioSesionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
    }

    private fun registrarEventos() {
        binding.btnIniciarSesion.setOnClickListener() {
            println("hola")
            //toolBa.visibility = View.VISIBLE
            findNavController()
                .navigate(R.id.action_inicioSesionFragment_to_nav_asistencia)
        }
    }


}