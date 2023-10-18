package com.itesm.aplicacioncomedor.view.voluntario

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.itesm.aplicacioncomedor.databinding.FragmentVoluntarioBinding
import com.itesm.aplicacioncomedor.viewmodel.SharedVM
import com.itesm.aplicacioncomedor.viewmodel.VoluntarioVM

class VoluntarioFragment : Fragment() {

    private lateinit var binding: FragmentVoluntarioBinding
    private val vmVoluntario: VoluntarioVM by viewModels()
    private val vmShared: SharedVM by activityViewModels()


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
        registrarObservadores()
    }

    private fun registrarObservadores() {
        vmVoluntario.exitoso.observe(viewLifecycleOwner, Observer{exitosa ->
            if (exitosa) {
                println("Exitosa es igual a true, se registró un voluntario")
                val nombreVoluntario = binding.tiNombre.text.toString()
                vmVoluntario.obtenerIdVol(nombreVoluntario)
            } else {
                mostrarDialogo("No se pudo registrar el Voluntario." +
                        "Comprueba tu conexión a internet.")
            }
        })
        vmVoluntario.voluntarioEncontrado.observe(viewLifecycleOwner, Observer{encontrado ->
            if (encontrado) {
                println("Encontrado es igual a true, se encontró un ID")
                val comedor = vmShared.idComedorSH.value
                println("Id de Comedor: ${comedor}")
                val voluntario = vmVoluntario.idVoluntario.value
                println("Voluntario: ${voluntario}")
                val rol = binding.spRoles.selectedItem.toString()
                println("Cocinero: ${rol}")
                if (comedor != null && voluntario != null){
                    vmVoluntario.enviarPersonal(comedor, voluntario, rol)
                }
            } else {
                mostrarDialogo("No se pudo registrar el Voluntario." +
                        "Comprueba tu conexión a internet ENCONTRADO.")
            }
        })
        vmVoluntario.exitosoApiPersonal.observe(viewLifecycleOwner, Observer{exitosaP ->
            if (exitosaP) {
                println("Se ha logrado con éxito, registrar y asignar voluntario")
                binding.tiNombre.text?.clear()
                binding.tiTelefono.text?.clear()
                binding.tiFechaNacimiento.text.clear()
            } else {
                mostrarDialogo("No se pudo registrar el Voluntario EXITOSAP.")
            }
        })
        vmVoluntario.conexionExitosa.observe(viewLifecycleOwner, Observer{conexion ->
            if (!conexion) {
                mostrarDialogo("No se pudo registrar el Voluntario, " +
                        "comprueba tu conexión a internet.")
            }})
    }


    private fun registrarEventos() {
        binding.btnFecha.setOnClickListener{
            showDatePickerDialog()
        }
        binding.btnRegVoluntario.setOnClickListener{
            val nombre = binding.tiNombre.text.toString()
            val telefono = binding.tiTelefono.text.toString()
            val fecha = binding.tiFechaNacimiento.text.toString()
            if(nombre.isEmpty() || telefono.isEmpty() || fecha.isEmpty()) {
                mostrarDialogo("Datos incompletos")
            }else{
                vmVoluntario.enviarVoluntario(nombre, fecha, telefono)
            }

        }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { dia, mes, anio -> onDateSelected(dia, mes, anio) }
        datePicker.show(childFragmentManager, "datePicker")
    }

    private fun onDateSelected(dia: Int, mes: Int, anio: Int) {
        val mesCorrecto= mes + 1
        binding.tiFechaNacimiento.setText("$anio-$mesCorrecto-$dia")
    }

    private fun mostrarDialogo(contenido: String){
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(contenido)
            .setTitle("Error")
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }
}