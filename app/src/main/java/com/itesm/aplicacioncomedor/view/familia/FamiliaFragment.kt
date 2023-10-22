package com.itesm.aplicacioncomedor.view.familia

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
import com.itesm.aplicacioncomedor.viewmodel.AsistenciaVM
import com.itesm.aplicacioncomedor.viewmodel.FamiliaViewModel

class FamiliaFragment : Fragment() {

    private var _binding: FragmentFamiliaBinding? = null
    private val binding get() = _binding!!
    private var adaptadorFamilia: AdaptadorFamilia? = null
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
        // Se comprueba que el ID del beneficiario se haya encontrado
        vmAsistencia.asistenteEncontrado.observe(viewLifecycleOwner, Observer {
            val benefiarioId = vmAsistencia.idAsistente.value
            val idFamilia = vmFamilia.idFamilia.value

            if (idFamilia != null && benefiarioId !=null) {
                vmFamilia.regitrarBenefFam(idFamilia,benefiarioId)
            } else{
                mostrarDialogo("Comprueba tu conexión")
            }
        })
        // Se verifica la conectividad a internet
        vmFamilia.conexionExitosa.observe(viewLifecycleOwner, Observer {conexion ->
            if(!conexion){
                mostrarDialogo("Comprueba tu conexión a internet")
            }
        })
        // Se verifica que se haya ingresado correctamente el beneficiario a una familia
        vmFamilia.familiaEncontrada.observe(viewLifecycleOwner, Observer {encontrado ->
            if(encontrado){
                vmFamilia.arrFamilia.forEach { elemento ->
                    val nombre = elemento.nombre
                    val fechaNacimiento = elemento.fechaNacimiento
                    vmAsistencia.obtenerBeneficiario(nombre, fechaNacimiento)
                }
                binding.etNombreFamilia.text.clear()
                mostrarDialogoExitoso("Familia Registrada")
            } else {
                mostrarDialogo("Comprueba tu conexión")
            }
        })
        // Verifica la creación de la familia
        vmFamilia.exitosoFamilia.observe(viewLifecycleOwner, Observer {agregado ->
            if(agregado){
                val nombre = binding.etNombreFamilia.text.toString()
                vmFamilia.obtenerIdFamilia(nombre)
            }else{
                mostrarDialogo("Comprueba tu conexión")
            }
        })
    }


    private fun registrarEventos() {
        binding.btnEnviarFamilia.setOnClickListener{
            val nombre = binding.etNombreFamilia.text.toString()
            if(nombre.isEmpty() || vmFamilia.arrFamilia.isEmpty()){
                mostrarDialogo("Revisa que tengas un nombre en la familia " +
                        "y al menos un integrante.")
            } else{
                vmFamilia.crearFamilia(nombre)
            }

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
        // Conectar el adaptador
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.VERTICAL
        binding.rvfamiliares.layoutManager = layout

        adaptadorFamilia = AdaptadorFamilia(requireContext(), arrFamilia, vmFamilia)
        binding.rvfamiliares.adapter = adaptadorFamilia
    }


    // En caso de falla se muestra este diálogo
    private fun mostrarDialogo(contenido: String){
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(contenido)
            .setTitle("Error")
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }

    // En caso de éxito se muestra este diálogo
    private fun mostrarDialogoExitoso(contenido: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(contenido)
            .setTitle("Éxito")
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