package com.itesm.aplicacioncomedor.view.iniciar_sesion

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.databinding.FragmentInicioSesionBinding
import com.itesm.aplicacioncomedor.viewmodel.FamiliaViewModel
import com.itesm.aplicacioncomedor.viewmodel.IniciarSesionVM
import com.itesm.aplicacioncomedor.viewmodel.SharedVM

class InicioSesionFragment : Fragment() {

    private lateinit var binding: FragmentInicioSesionBinding

    private val vmIniciarSesion: IniciarSesionVM by viewModels()
    private val vmShared: SharedVM by activityViewModels()
    private val vmPrueba: FamiliaViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentInicioSesionBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
        registrarObservadores()
    }


    private fun registrarObservadores() {
        vmIniciarSesion.conexionExitosa.observe(viewLifecycleOwner, Observer { conectado ->
            if (conectado == false) {
                mostrarDialogo("Compruebe la conexión a internet.")
            }
        })
        vmIniciarSesion.autenticacionExitosa.observe(viewLifecycleOwner, Observer { exitosa ->
            if (exitosa) {
                val nombreComedor = binding.etUsuarioIS.text.toString()
                vmShared.nombreComedorSH.value = nombreComedor
                vmShared.obtenerIdComedor(nombreComedor)
                findNavController().navigate(R.id.action_inicioSesionFragment_to_nav_asistencia)
            } else {
                mostrarDialogo("Datos incorrectos.")
            }
        })
    }

    private fun registrarEventos() {
        binding.btnIniciarSesion.setOnClickListener{

            val usuario = binding.etUsuarioIS.text.toString()
            val contrasena = binding.etContrasenaIS.text.toString()

            if(usuario.isEmpty() || contrasena.isEmpty()){
                mostrarDialogo("Al menos uno de los campos esta vacío.")
            }else{
                // Puerta trasera
                if (usuario == "admin" && contrasena == "root"){
                    findNavController().navigate(R.id.action_inicioSesionFragment_to_nav_asistencia)
                }else{
                    vmIniciarSesion.autentificaUsuario(usuario, contrasena)
                }
            }
        }
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
    // Desbloquea el Drawer
    override fun onDestroyView() {
        super.onDestroyView()
    }
}