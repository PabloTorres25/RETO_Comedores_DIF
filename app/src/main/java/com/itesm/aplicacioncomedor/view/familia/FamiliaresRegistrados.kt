package com.itesm.aplicacioncomedor.view.familia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.aplicacioncomedor.databinding.FragmentFamiliaresRegistradosBinding
import com.itesm.aplicacioncomedor.model.FechaCurp
import com.itesm.aplicacioncomedor.viewmodel.AsistenciaVM
import com.itesm.aplicacioncomedor.viewmodel.FamiliaViewModel


class FamiliaresRegistrados : Fragment() {

    private lateinit var binding: FragmentFamiliaresRegistradosBinding  // Declara el objeto de vinculación

    private var adaptadorRegistrosFamilia: AdaptadorRegistrosFamilia? = null
    private val vm: AsistenciaVM by viewModels()    // Utilizamos el mismo viewModel que en Asistencia

    private val familiaViewModel: FamiliaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFamiliaresRegistradosBinding.inflate(layoutInflater)// Accede al RecyclerView dentro de onCreateView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configurarRV()
        filtarLista()
    }

    // Función donde se hace el filtro para la barra de busqueda
    private fun filtarLista() {
        vm.listaAsistente.observe(viewLifecycleOwner) { listaCompleta ->
            binding.etBuscadorFamiliasRegistradas.addTextChangedListener { editableText ->
                val nombreFiltrado = editableText.toString()
                val listaFiltrada = listaCompleta.filter {
                    it.nombre.contains(nombreFiltrado, ignoreCase = true)
                }.toTypedArray()
                adaptadorRegistrosFamilia?.actualizarArreglo(listaFiltrada)
            }
        }
    }

    private fun configurarRV() {
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.VERTICAL
        binding.rvfamiliaresRegistrados.layoutManager = layout
        // Se conecta el adaptador
        vm.listaAsistente.observe(viewLifecycleOwner){lista ->
            val arrAsistentes = lista.toTypedArray()
            val fechaaEdad = FechaCurp()
            adaptadorRegistrosFamilia = AdaptadorRegistrosFamilia(requireContext(), arrAsistentes, familiaViewModel, fechaaEdad)// { onItemSelected(it) }
            binding.rvfamiliaresRegistrados.adapter = adaptadorRegistrosFamilia
        }
    }
    override fun onStart() {
        super.onStart()
        vm.registrarAsistentes()
    }
}