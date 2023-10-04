package com.itesm.aplicacioncomedor.view.voluntario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.itesm.aplicacioncomedor.databinding.FragmentVoluntarioBinding
import com.itesm.aplicacioncomedor.viewmodel.voluntario.VoluntarioVM

class VoluntarioFragment : Fragment() {

    private lateinit var binding: FragmentVoluntarioBinding
    private val viewModelVoluntario: VoluntarioVM by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVoluntarioBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
    }

    private fun registrarEventos() {
        binding.btnFecha.setOnClickListener{
            showDatePickerDialog() }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { dia, mes, anio -> onDateSelected(dia, mes, anio) }
        datePicker.show(childFragmentManager, "datePicker")
    }

    private fun onDateSelected(dia: Int, mes: Int, anio: Int) {
        val mesCorrecto= mes + 1
        binding.tiFechaNacimiento.setText("$anio-$mesCorrecto-$dia")
    }
}