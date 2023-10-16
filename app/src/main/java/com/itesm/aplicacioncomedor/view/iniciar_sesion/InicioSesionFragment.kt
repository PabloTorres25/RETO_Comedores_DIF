package com.itesm.aplicacioncomedor.view.iniciar_sesion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.databinding.FragmentInicioSesionBinding
import com.itesm.aplicacioncomedor.model.ToastUtil
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
        registrarObservadores()
    }


    private fun registrarObservadores() {
        vm.conexionExitosa.observe(viewLifecycleOwner, Observer { conectado ->
            if (conectado == false) {
                ToastUtil.mostrarToast(requireContext(), "No hay conexión")
            }
        })
        vm.autenticacionExitosa.observe(viewLifecycleOwner, Observer { exitosa ->
            if (exitosa) {
                findNavController().navigate(R.id.action_inicioSesionFragment_to_nav_asistencia)
            } else {
                ToastUtil.mostrarToast(requireContext(), "Usuario o Contraseña Incorrectos")
            }
        })
    }

    private fun registrarEventos() {
        binding.btnIniciarSesion.setOnClickListener{

            val usuario = binding.etUsuarioIS.text.toString()
            val contrasena = binding.etContrasenaIS.text.toString()

            if(usuario.isEmpty() || contrasena.isEmpty()){
                ToastUtil.mostrarToast(requireContext(), "Datos incompletos")
            }else{
                // Puerta trasera
                if (usuario == "admin" && contrasena == "root"){
                    findNavController().navigate(R.id.action_inicioSesionFragment_to_nav_asistencia)
                }else{
                    vm.autentificaUsuario(usuario, contrasena)
                }
            }
        }
    }
    // Desbloquea el Drawer
    override fun onDestroyView() {
        super.onDestroyView()
    }
}