package com.itesm.aplicacioncomedor.view.iniciar_sesion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.databinding.FragmentInicioSesionBinding
import com.itesm.aplicacioncomedor.viewmodel.IniciarSesionVM

class InicioSesionFragment : Fragment() {

    private lateinit var binding: FragmentInicioSesionBinding

    private val vm: IniciarSesionVM by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentInicioSesionBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
        //registrarObservadores()
    }
    /*

    private fun registrarObservadores() {
        vm.autenticacionExitosa.observe(viewLifecycleOwner, Observer { exitosa ->
            if (exitosa) {
                findNavController().navigate(R.id.action_inicioSesionFragment_to_nav_asistencia)
            } else {
                println("Hola")
            }
        })
    }
     */
    private fun registrarEventos() {
        binding.btnIniciarSesion.setOnClickListener() {
            /*
            val usuario = binding.etUsuarioIS.text.toString()
            val contrasena = binding.etContrasenaIS.text.toString()
            vm.autentificaUsuario(usuario, contrasena)
            */
            findNavController().navigate(R.id.action_inicioSesionFragment_to_nav_asistencia)
        }
    }
    // Desbloquea el Drawer
    override fun onDestroyView() {
        super.onDestroyView()
    }
}