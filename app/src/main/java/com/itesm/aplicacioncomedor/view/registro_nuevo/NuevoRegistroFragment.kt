package com.itesm.aplicacioncomedor.view.registro_nuevo

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.databinding.FragmentNuevoRegistroBinding

class NuevoRegistroFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentNuevoRegistroBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNuevoRegistroBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Clic en btnEnviarRegistro
        binding.btnEnviarRegistro.setOnClickListener {
            findNavController().navigate(R.id.action_nav_nuevo_registro_to_nav_asistencia)
        }
        // Clic en btnCondicion
        binding.btnCondicion.setOnClickListener {
            showDialog()
        }
        return root
    }

    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_sheet_layout)

        val btnListo = dialog.findViewById<ImageButton>(R.id.btnListoCondiciones)
        val chipGroup = dialog.findViewById<ChipGroup>(R.id.cgCondiciones)

        btnListo.setOnClickListener {
            val selectedChips = StringBuilder()

            for (i in 0 until chipGroup.childCount) {
                val child: View = chipGroup.getChildAt(i)

                if (child is Chip) {
                    val chip: Chip = child as Chip

                    if (chip.isChecked) {
                        if (selectedChips.isNotEmpty()) {
                            selectedChips.append(", ")
                        }
                        selectedChips.append(chip.text)
                    }
                }
            }

            val message: String = if (selectedChips.isNotEmpty()) {
                selectedChips.toString()
            } else {
                "No se seleccionaron chips."
            }

            binding.tvCondiciones.setText(message)
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }



    override fun onResume(){
        super.onResume()
        // Spinner Tipo de Asistencia
        val types = resources.getStringArray(R.array.tipos_asistencia)
        val adapterAsistencia = ArrayAdapter(requireContext(), R.layout.dropdown_item, types)
        binding.spTipoAsistencia.adapter = adapterAsistencia
        binding.spTipoAsistencia.onItemSelectedListener = this
        // Spinner Sexo
        val genders = resources.getStringArray(R.array.generos)
        val adapterSexo = ArrayAdapter(requireContext(), R.layout.dropdown_item, genders)
        binding.spSexo.adapter = adapterSexo
        binding.spSexo.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.spTipoAsistencia -> {
                // Obtener el elemento seleccionado y convertirlo a String
                val selectedItem = parent?.getItemAtPosition(position)?.toString()
                // ToastUtil.mostrarToast(requireContext(), "Opcion seleccionada: $selectedItem")
            }
            R.id.spSexo -> {
                // Obtener el elemento seleccionado y convertirlo a String
                val selectedItem = parent?.getItemAtPosition(position)?.toString()
                // ToastUtil.mostrarToast(requireContext(), "Opcion seleccionada: $selectedItem")
            }
        }

    }
    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}