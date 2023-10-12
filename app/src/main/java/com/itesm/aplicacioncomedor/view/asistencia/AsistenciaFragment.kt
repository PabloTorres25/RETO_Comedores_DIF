package com.itesm.aplicacioncomedor.view.asistencia

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.databinding.FragmentAsistenciaBinding
import com.itesm.aplicacioncomedor.model.ToastUtil
import com.itesm.aplicacioncomedor.model.asistencia.Asistentes

class AsistenciaFragment : Fragment()  {

    private lateinit var binding: FragmentAsistenciaBinding
    var adaptadorAsistentes: AdaptadorAsistentes? = null

    //Animaciones de los Fab
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_anim)}

    private var masClicked = false  // Switch de el fabNuevoRegistro

    private val arrAsistentes = arrayOf(
        Asistentes("Pablo David Torres Granados", 20),
        Asistentes("Eduardo Alfredo Ramírez Muñoz", 20),
        Asistentes("Luis David Maza Ramírez", 35),
        Asistentes("Diego Zurita Villarreal", 59),
        Asistentes("Julián Cisneros Cortés", 47)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAsistenciaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Buscador
        binding.etBuscador.addTextChangedListener{ userFilter->
            val arrayFiltrado = arrAsistentes.filter {
                it.nombre.contains(userFilter.toString(), ignoreCase = true)
            }
            adaptadorAsistentes?.actualizarArreglo(arrayFiltrado.toTypedArray())
        }

        configurarRV()
        registrarEventos()
    }



    private fun configurarRV() {

        val layout = LinearLayoutManager(requireContext())          //1
        layout.orientation = LinearLayoutManager.VERTICAL

        binding.rvAsistentes.layoutManager = layout
        adaptadorAsistentes =
            AdaptadorAsistentes(requireContext(), arrAsistentes) { onItemSelected(it) }
        binding.rvAsistentes.adapter = adaptadorAsistentes
    }

    fun onItemSelected(asitente: Asistentes){
        ToastUtil.mostrarToast(requireContext(), "Algo esta pasando")
    }
    private fun registrarEventos() {
        // Clic en fabNuevoRegistro
        binding.fabNuevoRegistro.setOnClickListener { view ->
            onfabNuevoregistroButtonClick()
        }
        // Clic en fabFamilia
        binding.fabFamilia.setOnClickListener { view ->
            findNavController().navigate(R.id.action_nav_asistencia_to_familiaFragment)
        }
        // Clic en fabPersona
        binding.fabPersona.setOnClickListener { view ->
            findNavController().navigate(R.id.action_nav_asistencia_to_nav_nuevo_registro)
        }
        // Clic en fabReporte
        binding.fabReporte.setOnClickListener { view ->
            findNavController().navigate(R.id.action_nav_asistencia_to_inicioSesionFragment)
        }
        masClicked = false

    }




    private fun onfabNuevoregistroButtonClick() {
        setVisibility(masClicked)
        setAnimation(masClicked)
        masClicked = !masClicked
    }
    private fun setVisibility(masClicked: Boolean) {
        if(!masClicked){
            binding.fabFamilia.visibility = View.VISIBLE
            binding.fabPersona.visibility = View.VISIBLE
            binding.tvFamiliaBarMain.visibility = View.VISIBLE
            binding.tvPersonaBarMain.visibility = View.VISIBLE
        }else{
            binding.fabFamilia.visibility = View.GONE
            binding.fabPersona.visibility = View.GONE
            binding.tvFamiliaBarMain.visibility = View.GONE
            binding.tvPersonaBarMain.visibility = View.GONE
        }
    }
    private fun setAnimation(masClicked: Boolean) {
        if(!masClicked){
            binding.fabFamilia.startAnimation(fromBottom)
            binding.fabPersona.startAnimation(fromBottom)
            binding.tvFamiliaBarMain.startAnimation(fromBottom)
            binding.tvPersonaBarMain.startAnimation(fromBottom)
            binding.fabNuevoRegistro.startAnimation(rotateOpen)
        }else{
            binding.fabFamilia.startAnimation(toBottom)
            binding.fabPersona.startAnimation(toBottom)
            binding.tvFamiliaBarMain.startAnimation(toBottom)
            binding.tvPersonaBarMain.startAnimation(toBottom)
            binding.fabNuevoRegistro.startAnimation(rotateClose)
        }
    }
}