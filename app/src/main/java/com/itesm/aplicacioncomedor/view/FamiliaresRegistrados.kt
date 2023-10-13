package com.itesm.aplicacioncomedor.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.databinding.FragmentFamiliaresRegistradosBinding
import com.itesm.aplicacioncomedor.model.ToastUtil
import com.itesm.aplicacioncomedor.model.asistencia.Asistentes
import com.itesm.aplicacioncomedor.model.asistencia.AsistentesData
import com.itesm.aplicacioncomedor.view.asistencia.AdaptadorAsistentes
import com.itesm.aplicacioncomedor.view.asistencia.AdaptadorRegistrosFamilia
import com.itesm.aplicacioncomedor.viewmodel.AsistenciaVM
import com.itesm.aplicacioncomedor.viewmodel.FamiliaresRegistradosViewModel


class FamiliaresRegistrados : Fragment() {

    private lateinit var binding: FragmentFamiliaresRegistradosBinding  // Declara el objeto de vinculación
    private lateinit var viewModel: FamiliaresRegistradosViewModel
    var adaptadorRegistrosFamilia: AdaptadorRegistrosFamilia? = null
    lateinit var recyclerView: RecyclerView // Declara una propiedad para el RecyclerView
    private val vm: AsistenciaVM by viewModels()    // Utilizamos el mismo viewModel que en Asistencia


    private val arrAsistentes = arrayOf(
        Asistentes("Pablo David Torres Granados", 20),
        Asistentes("Eduardo Alfredo Ramírez Muñoz", 20),
        Asistentes("Luis David Maza Ramírez", 35),
        Asistentes("Diego Zurita Villarreal", 59),
        Asistentes("Julián Cisneros Cortés", 47)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFamiliaresRegistradosBinding.inflate(inflater, container, false)
        recyclerView = binding.root.findViewById(R.id.rvfamiliares_registrados) // Accede al RecyclerView dentro de onCreateView

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // viewModel = ViewModelProvider(this).get(FamiliaresRegistradosViewModel::class.java)
        vm.listaAsistente.observe(viewLifecycleOwner) { listaCompleta ->
            binding.etBuscadorFamiliasRegistradas.addTextChangedListener { editableText ->
                val nombreFiltrado = editableText.toString()
                val listaFiltrada = listaCompleta.filter {
                    it.nombre.contains(nombreFiltrado, ignoreCase = true)
                }
                adaptadorRegistrosFamilia?.actualizarArreglo(listaFiltrada.toTypedArray())
            }
        }
        configurarRV()
        registrarEventos()
    }

    private fun registrarEventos() {
        // Fab registrados
        binding.fabFamiliaresRegistrados.setOnClickListener{
            // findNavController().navigate(R.id.action_familiaresRegistrados_to_familiaFragment)
            ToastUtil.mostrarToast(requireContext(), "Murcielago")
        }
    }


    override fun onStart() {
        super.onStart()
        vm.registrarAsistentes()
    }

    private fun configurarRV() {
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layout

        vm.listaAsistente.observe(viewLifecycleOwner){lista ->
            val arrAsistentes = lista.toTypedArray()
            adaptadorRegistrosFamilia = AdaptadorRegistrosFamilia(requireContext(), arrAsistentes) { onItemSelected(it) }
        }

        recyclerView.adapter = adaptadorRegistrosFamilia
    }

    fun onItemSelected(asistente: AsistentesData){
        ToastUtil.mostrarToast(requireContext(), "Algo esta pasando")
    }

}