package com.itesm.aplicacioncomedor.ui.asistencia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.aplicacioncomedor.databinding.FragmentAsistenciaBinding
import com.itesm.aplicacioncomedor.model.Asistentes
import com.itesm.aplicacioncomedor.view.AdaptadorAsistentes

class AsistenciaFragment : Fragment() {

    private var _binding: FragmentAsistenciaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var adaptadorAsistentes: AdaptadorAsistentes? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAsistenciaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val arrAsistentes = arrayOf(
            Asistentes("Pablo David Torres Granados", 20),
            Asistentes("Eduardo Alfredo Ramírez Muñoz", 20),
            Asistentes("Luis David Maza Ramírez", 35),
            Asistentes("Diego Zurita Villarreal", 59),
            Asistentes("Julián Cisneros Cortés", 47)
        )

        val layout = LinearLayoutManager(requireContext())          //1
        layout.orientation = LinearLayoutManager.VERTICAL
        binding.rvAsistentes.layoutManager = layout

        adaptadorAsistentes = AdaptadorAsistentes(requireContext(), arrAsistentes)
        binding.rvAsistentes.adapter = adaptadorAsistentes

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}