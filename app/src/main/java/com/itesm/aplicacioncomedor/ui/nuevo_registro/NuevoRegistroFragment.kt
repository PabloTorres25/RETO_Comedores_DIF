package com.itesm.aplicacioncomedor.ui.nuevo_registro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.databinding.FragmentNuevoRegistroBinding

class NuevoRegistroFragment : Fragment() {

    private var _binding: FragmentNuevoRegistroBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentNuevoRegistroBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // Mi codigo
        // Clic en btnEnviarRegistro
        binding.btnEnviarRegistro.setOnClickListener {
            findNavController().navigate(R.id.action_nav_nuevo_registro_to_nav_asistencia)
        }

        // Clic en cbSoloRegistro
        binding.cbSoloRegistro.setOnClickListener { view ->
            if (binding.cbSoloRegistro.isChecked){
                binding.cbPagado.visibility = View.GONE
                binding.cbPresente.visibility = View.GONE
            } else{
                binding.cbPagado.visibility = View.VISIBLE
                binding.cbPresente.visibility = View.VISIBLE
            }
        }
        // Fin de Mi codigo
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}