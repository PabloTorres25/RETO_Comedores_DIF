package com.itesm.aplicacioncomedor.view.asistencia

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.databinding.FragmentAsistenciaBinding
import com.itesm.aplicacioncomedor.model.asistencia.AsistentesData
import com.itesm.aplicacioncomedor.viewmodel.AsistenciaVM


class AsistenciaFragment : Fragment()  {

    private lateinit var binding: FragmentAsistenciaBinding
    var adaptadorAsistentes: AdaptadorAsistentes? = null
    private val vm: AsistenciaVM by viewModels()

    //Animaciones de los Fab
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_anim)}

    private var masClicked = false  // Switch de el fabNuevoRegistro


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAsistenciaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        filtraLista()
        configurarRV()
        registrarEventos()
        configSwipe()
    }

    private fun filtraLista() {
        vm.listaAsistente.observe(viewLifecycleOwner) { listaCompleta ->
            binding.etBuscador.addTextChangedListener { editableText ->
                val nombreFiltrado = editableText.toString()
                val listaFiltrada = listaCompleta.filter {
                    it.nombre.contains(nombreFiltrado, ignoreCase = true)
                }
                adaptadorAsistentes?.actualizarArreglo(listaFiltrada.toTypedArray())
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun configSwipe() {
        binding.swRefresh.setColorSchemeColors(R.color.colorToolBar)
        binding.swRefresh.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swRefresh.isRefreshing = false
                vm.registrarAsistentes()
                configurarRV()
            }, 2000)
        }
    }


    private fun configurarRV() {
        val layout = LinearLayoutManager(requireContext())          //1
        layout.orientation = LinearLayoutManager.VERTICAL
        binding.rvAsistentes.layoutManager = layout
        // Conectar el adaptador
        vm.listaAsistente.observe(viewLifecycleOwner){lista ->
            val arrAsistente = lista.toTypedArray()
            adaptadorAsistentes = AdaptadorAsistentes(requireContext(), arrAsistente)
            binding.rvAsistentes.adapter = adaptadorAsistentes
        }
    }


    override fun onStart() {
        super.onStart()
        vm.registrarAsistentes()
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


