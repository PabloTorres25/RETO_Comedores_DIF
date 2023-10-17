package com.itesm.aplicacioncomedor.view.asistencia

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.databinding.FragmentAsistenciaBinding
import com.itesm.aplicacioncomedor.model.FechaCurp
import com.itesm.aplicacioncomedor.viewmodel.AsistenciaVM
import com.itesm.aplicacioncomedor.viewmodel.FamiliaViewModel
import com.itesm.aplicacioncomedor.viewmodel.SharedVM
import java.util.Date
import java.util.Locale


class AsistenciaFragment : Fragment()  {

    private lateinit var binding: FragmentAsistenciaBinding
    var adaptadorAsistentes: AdaptadorAsistentes? = null
    private val vmAsistencia: AsistenciaVM by viewModels()
    private val vmShared: SharedVM by activityViewModels()

    //Animaciones de los Fab
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_anim)}

    private var masClicked = false  // Switch de el fabNuevoRegistro

    private val familiaViewModel: FamiliaViewModel by activityViewModels() //ViewModel de familia para reiniciar la lista cada que se crea


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAsistenciaBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configurarRV()
        adaptadorAsistentes = AdaptadorAsistentes(requireContext(), emptyArray(), FechaCurp())
        binding.rvAsistentes.adapter = adaptadorAsistentes
        configSwipe()
        filtraLista()
        registrarObservadores()
        registrarEventos()
    }

    private fun registrarObservadores() {
        adaptadorAsistentes?.btnAceptar?.observe(viewLifecycleOwner, Observer {cambio ->
            if(cambio){
                val comedor = vmShared.idComedorSH.value
                val nombre = adaptadorAsistentes?.nombreBenef?.value
                val fecha = adaptadorAsistentes?.fechaBenef?.value
                val pagado = adaptadorAsistentes?.pagado?.value
                val presente = adaptadorAsistentes?.presente?.value
                val fechaAsistencia = obtenerFechaHoraActual()
                println("Id de Comedor: ${comedor}")
                println("Nombre: ${nombre}")
                println("Fecha de nacimiento: ${fecha}")
                println("Pagado?: ${pagado}")
                println("Presente?: ${presente}")
                println("Fecha Asis: ${fechaAsistencia}")
                if (nombre != null && fecha != null) {
                    vmAsistencia.obtenerBeneficiario(nombre, fecha)
                }
            }
        })
        vmAsistencia.asistenteEncontrado.observe(viewLifecycleOwner, Observer { encontrado ->
            if(encontrado){
                val comedor = vmShared.idComedorSH.value
                val benefiarioId = vmAsistencia.idAsistente.value
                val pagado = adaptadorAsistentes?.pagado?.value
                val presente = adaptadorAsistentes?.presente?.value
                val fechaAsistencia = obtenerFechaHoraActual()
                if (comedor != null && benefiarioId != null
                    && pagado != null && presente != null) {
                    println("Id de Comedor: ${comedor}")
                    println("Numero Benef: ${benefiarioId}")
                    println("Pagado?: ${pagado}")
                    println("Presente?: ${presente}")
                    println("Fecha Asis: ${fechaAsistencia}")
                    vmAsistencia.registrarAsistencia(comedor, benefiarioId, fechaAsistencia,
                        pagado, presente)
                }
            }
        })
    }


    private fun filtraLista() {
        vmAsistencia.listaAsistente.observe(viewLifecycleOwner) { listaCompleta ->
            binding.etBuscador.addTextChangedListener { editableText ->
                val nombreFiltrado = editableText.toString()
                val listaFiltrada = listaCompleta.filter {
                    it.nombre.contains(nombreFiltrado, ignoreCase = true)
                }
                adaptadorAsistentes?.actualizarArreglo(listaFiltrada.toTypedArray())
                if (nombreFiltrado.isEmpty()) {
                    binding.swRefresh.visibility = View.GONE
                } else {
                    binding.swRefresh.visibility = View.VISIBLE
                }
            }
        }
    }


    @SuppressLint("ResourceAsColor")
    private fun configSwipe() {
        binding.swRefresh.setColorSchemeColors(R.color.colorToolBar)
        binding.swRefresh.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swRefresh.isRefreshing = false
                vmAsistencia.registrarAsistentes()
                configurarRV()
            }, 2000)
        }
    }


    private fun configurarRV() {
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.VERTICAL
        binding.rvAsistentes.layoutManager = layout

        // Inicializa el adaptador una vez
        val fechaaEdad = FechaCurp()
        adaptadorAsistentes = AdaptadorAsistentes(requireContext(), emptyArray(), fechaaEdad)
        binding.rvAsistentes.adapter = adaptadorAsistentes

        vmAsistencia.listaAsistente.observe(viewLifecycleOwner) { lista ->
            val arrAsistente = lista.toTypedArray()
            // Actualiza los datos del adaptador sin crear uno nuevo
            adaptadorAsistentes?.actualizarArreglo(arrAsistente)
        }

    }


    override fun onStart() {
        super.onStart()
        vmAsistencia.registrarAsistentes()
    }

    private fun registrarEventos() {
        // Clic en fabNuevoRegistro
        binding.fabNuevoRegistro.setOnClickListener { view ->
            onfabNuevoregistroButtonClick()
        }
        // Clic en fabFamilia
        binding.fabFamilia.setOnClickListener { view ->
            familiaViewModel.arrFamilia.clear()
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
    fun obtenerFechaHoraActual(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("America/Mexico_City")
        return sdf.format(Date())
    }
}


