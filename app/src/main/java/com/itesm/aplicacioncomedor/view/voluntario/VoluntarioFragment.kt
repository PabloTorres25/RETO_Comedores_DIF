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
/*
* Vista para registrar un Voluntario
*/
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
        // Comprueba si el voluntario se registró
        vmVoluntario.exitoso.observe(viewLifecycleOwner, Observer{exitosa ->
            if (exitosa) {
                val nombreVoluntario = binding.tiNombre.text.toString()
                vmVoluntario.obtenerIdVol(nombreVoluntario)
            } else {
                mostrarDialogo("No se pudo registrar el Voluntario. " +
                        "Comprueba tu conexión a internet.")
            }
        })
        // Se comprueba si el ID del voluntario fue encontrado
        vmVoluntario.voluntarioEncontrado.observe(viewLifecycleOwner, Observer{encontrado ->
            if (encontrado) {
                val comedor = vmShared.idComedorSH.value
                val voluntario = vmVoluntario.idVoluntario.value
                val rol = binding.spRoles.selectedItem.toString()
                println("Id de Comedor: ${comedor}")
                println("Voluntario: ${voluntario}")
                println("Cocinero: ${rol}")
                if (comedor != null && voluntario != null){
                    vmVoluntario.enviarPersonal(comedor, voluntario, rol)
                }
            } else {
                mostrarDialogo("No se pudo registrar el Voluntario. " +
                        "Comprueba tu conexión a internet.")
            }
        })
        // Se observa si se completó el registro
        vmVoluntario.exitosoApiPersonal.observe(viewLifecycleOwner, Observer{exitosaP ->
            if (exitosaP) {
                binding.tiNombre.text?.clear()
                binding.tiTelefono.text?.clear()
                binding.tiFechaNacimiento.text.clear()
                mostrarDialogoExitoso("Voluntario Registrado")
            } else {
                mostrarDialogo("No se pudo registrar el Voluntario.")
            }
        })
        // Comprueba que la conexión sea exitosa
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

    // Date Picker para escoger una fecha de nacimiento
    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { dia, mes, anio -> onDateSelected(dia, mes, anio) }
        datePicker.show(childFragmentManager, "datePicker")
    }

    private fun onDateSelected(dia: Int, mes: Int, anio: Int) {
        val mesCorrecto= mes + 1
        binding.tiFechaNacimiento.setText("$anio-$mesCorrecto-$dia")
    }

    // En caso de falla se muestra este diálogo
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

    // En caso de éxito se muestra este diálogo
    private fun mostrarDialogoExitoso(contenido: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(contenido)
            .setTitle("Éxito")
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }
}