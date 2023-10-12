package com.itesm.aplicacioncomedor.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.databinding.FragmentFamiliaresRegistradosBinding
import com.itesm.aplicacioncomedor.model.ToastUtil
import com.itesm.aplicacioncomedor.model.asistencia.Asistentes
import com.itesm.aplicacioncomedor.view.asistencia.AdaptadorAsistentes
import com.itesm.aplicacioncomedor.view.asistencia.AdaptadorRegistrosFamilia
import com.itesm.aplicacioncomedor.viewmodel.FamiliaresRegistradosViewModel


class FamiliaresRegistrados : Fragment() {

    private lateinit var binding: FragmentFamiliaresRegistradosBinding  // Declara el objeto de vinculación
    private lateinit var viewModel: FamiliaresRegistradosViewModel
    var adaptadorRegistrosFamilia: AdaptadorRegistrosFamilia? = null
    lateinit var recyclerView: RecyclerView // Declara una propiedad para el RecyclerView


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
        viewModel = ViewModelProvider(this).get(FamiliaresRegistradosViewModel::class.java)
        binding.fabFamiliaresRegistrados.setOnClickListener{
            findNavController().navigate(R.id.action_familiaresRegistrados_to_familiaFragment)
        }
        configurarRV()
    }

    private fun configurarRV() {
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layout
        adaptadorRegistrosFamilia =
            AdaptadorRegistrosFamilia(requireContext(), arrAsistentes) { onItemSelected(it) }
        recyclerView.adapter = adaptadorRegistrosFamilia
    }
    fun onItemSelected(asitente: Asistentes){
        ToastUtil.mostrarToast(requireContext(), "Algo esta pasando")
    }

}