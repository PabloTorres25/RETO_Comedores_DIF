package com.itesm.aplicacioncomedor.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.databinding.FragmentFamiliaBinding
import com.itesm.aplicacioncomedor.model.ToastUtil
import com.itesm.aplicacioncomedor.model.asistencia.Asistentes
import com.itesm.aplicacioncomedor.model.asistencia.AsistentesData
import com.itesm.aplicacioncomedor.view.asistencia.AdaptadorFamilia
import com.itesm.aplicacioncomedor.view.asistencia.AdaptadorRegistrosFamilia
import com.itesm.aplicacioncomedor.viewmodel.AsistenciaVM
import com.itesm.aplicacioncomedor.viewmodel.FamiliaViewModel

class FamiliaFragment : Fragment() {

    private var _binding: FragmentFamiliaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val vm: AsistenciaVM by viewModels()    // Utilizamos el mismo viewModel que en Asistencia
    var adaptadorFamilia: AdaptadorFamilia? = null
    private val familiaViewModel: FamiliaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFamiliaBinding.inflate(inflater, container, false)


        // Mi codigo

        val arrFamilia = familiaViewModel.arrFamilia        // Array de la familia

        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.VERTICAL
        binding.rvfamiliares.layoutManager = layout

        adaptadorFamilia = AdaptadorFamilia(requireContext(), arrFamilia)// { onItemSelected(it) }
        binding.rvfamiliares.adapter = adaptadorFamilia

        binding.btnAgregarIntegranteFamilia.setOnClickListener {
            val items = arrayOf("Registro Existente", "Nuevo Registro")
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Selecciona una opción")
            alertDialog.setItems(items) { dialog, which ->
                when (items[which]) {
                    "Registro Existente" -> {
                        // Navegar a FragmentOpcion1
                        findNavController().navigate(R.id.action_familiaFragment_to_familiaresRegistrados)
                    }
                    "Nuevo Registro" -> {
                        // Navegar a FragmentOpcion2
                        findNavController().navigate(R.id.action_familiaFragment_to_nav_nuevo_registro)
                    }
                }
            }

            alertDialog.show()
        }

        // Fin de Mi codigo


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}