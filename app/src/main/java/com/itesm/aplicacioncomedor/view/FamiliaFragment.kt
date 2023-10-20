package com.itesm.aplicacioncomedor.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.databinding.FragmentFamiliaBinding
import com.itesm.aplicacioncomedor.view.asistencia.AdaptadorFamilia
import com.itesm.aplicacioncomedor.viewmodel.AsistenciaVM
import com.itesm.aplicacioncomedor.viewmodel.FamiliaViewModel

class FamiliaFragment : Fragment() {

    private var _binding: FragmentFamiliaBinding? = null
    private val binding get() = _binding!!
    var adaptadorFamilia: AdaptadorFamilia? = null
    private val vmFamilia: FamiliaViewModel by activityViewModels()
    private val vmAsistencia: AsistenciaVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFamiliaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarRV()
        registrarEventos()
        registrarObservadores()
    }
    private fun registrarObservadores() {
        vmAsistencia.asistenteEncontrado.observe(viewLifecycleOwner, Observer {
            val benefiarioId = vmAsistencia.idAsistente.value
            val idFamilia = vmFamilia.idFamilia.value
            println("LLEGÓ ACÁ Y EL ID ES ${benefiarioId}")
            println("LLEGÓ ACÁ Y EL IDFam ES ${idFamilia}")

            if (idFamilia != null && benefiarioId !=null) {
                vmFamilia.regitrarBenefFam(idFamilia,benefiarioId)
            } else{
                println("No entro a registrar BENEF FAM")
            }
        })

        vmFamilia.familiaEncontrada.observe(viewLifecycleOwner, Observer {encontrado ->
            if(encontrado){
                vmFamilia.arrFamilia.forEach { elemento ->
                    val nombre = elemento.nombre
                    val fechaNacimiento = elemento.fechaNacimiento
                    vmAsistencia.obtenerBeneficiario(nombre, fechaNacimiento)
                }
                mostrarDialogoExitoso("Familia Registrada")
            } else {
                println("DIALOGO")
            }
        })

        vmFamilia.exitosoFamilia.observe(viewLifecycleOwner, Observer {agregado ->
            if(agregado){
                println("llegue")
                val nombre = binding.etNombreFamilia.text.toString()
                vmFamilia.obtenerIdFamilia(nombre)
            }else{
                println("DIALOGO")
            }
        })
    }


    private fun registrarEventos() {
        binding.btnEnviarFamilia.setOnClickListener{
            val nombre = binding.etNombreFamilia.text.toString()
            vmFamilia.crearFamilia(nombre)
        }


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
    }

    private fun configurarRV() {
        val arrFamilia = vmFamilia.arrFamilia        // Array de la familia

        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.VERTICAL
        binding.rvfamiliares.layoutManager = layout

        adaptadorFamilia = AdaptadorFamilia(requireContext(), arrFamilia, vmFamilia)// { onItemSelected(it) }
        binding.rvfamiliares.adapter = adaptadorFamilia
    }


    private fun mostrarDialogoExitoso(contenido: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(contenido)
            .setTitle("Exito")
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}