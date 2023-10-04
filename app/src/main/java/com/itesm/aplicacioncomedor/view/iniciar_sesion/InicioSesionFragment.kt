package com.itesm.aplicacioncomedor.view.iniciar_sesion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.databinding.FragmentInicioSesionBinding

class InicioSesionFragment : Fragment() {

    private lateinit var binding: FragmentInicioSesionBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentInicioSesionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
    }



    private fun registrarEventos() {
        binding.btnIniciarSesion.setOnClickListener() {
            findNavController()
                .navigate(R.id.action_inicioSesionFragment_to_nav_asistencia)
        }
    }
}