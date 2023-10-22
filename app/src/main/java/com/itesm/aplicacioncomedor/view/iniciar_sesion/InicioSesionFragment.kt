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
import com.itesm.aplicacioncomedor.viewmodel.IniciarSesionVM
import com.itesm.aplicacioncomedor.viewmodel.SharedVM

class InicioSesionFragment : Fragment() {

    private lateinit var binding: FragmentInicioSesionBinding

    private val vmIniciarSesion: IniciarSesionVM by viewModels()
    private val vmShared: SharedVM by activityViewModels()


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
        // Se verifica la conectividad a internet
        vmIniciarSesion.conexionExitosa.observe(viewLifecycleOwner, Observer { conexion ->
            if(!conexion){
                mostrarDialogo("Comprueba tu conexión a internet")
            }
        })
        // Se autentifica el usuario y su contraseña
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
        // Click en iniciar sesión
        binding.btnIniciarSesion.setOnClickListener{

            val usuario = binding.etUsuarioIS.text.toString()
            val contrasena = binding.etContrasenaIS.text.toString()

            if(usuario.isEmpty() || contrasena.isEmpty()){
                mostrarDialogo("Al menos uno de los campos esta vacío.")
            }else{
                    vmIniciarSesion.autentificaUsuario(usuario, contrasena)
            }
        }
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

}