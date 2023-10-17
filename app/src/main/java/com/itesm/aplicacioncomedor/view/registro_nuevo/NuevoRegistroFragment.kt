package com.itesm.aplicacioncomedor.view.registro_nuevo

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.zxing.integration.android.IntentIntegrator
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.databinding.FragmentNuevoRegistroBinding
import com.itesm.aplicacioncomedor.model.FechaaEdadCurp
import com.itesm.aplicacioncomedor.model.ToastUtil
import com.itesm.aplicacioncomedor.viewmodel.RegistroNuevoVM


class NuevoRegistroFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentNuevoRegistroBinding? = null
    private val vm: RegistroNuevoVM by viewModels()

    // Camara


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val selectedChipsSet = HashSet<Int>()       // Guarda el Id de los Chips para que cuando el BottomSheet vuelva a salir sigan achivos

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNuevoRegistroBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
    }

    private fun registrarEventos() {
        val curp = binding.etCurpnRegistro.text.toString()
        val nombre = binding.etNombrenRegistro.text.toString()
        val edad = binding.etEdadnRegistro.text.toString()
        //val direccion = binding.etDireccionnRegistro.text.toString()

        binding.etCurpnRegistro.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No es necesario implementar esta función
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Este método se llama cuando el texto de curp cambia

                // Con el Curp define Edad
                val curpChar = s.toString()
                println("curp = ${curpChar}")
                print("Tamaño curp ${curpChar.length}")
                if (curpChar.length >= 10) {
                    try {
                        val calculadoraEdad = FechaaEdadCurp()
                        val edadcurp = calculadoraEdad.fechanacimientoaEdad(curpChar)
                        binding.etEdadnRegistro.setText(edadcurp.toString())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {
                // No es necesario implementar esta función
            }
        })
        // Clic en btnEnviarRegistro
        binding.btnEnviarRegistro.setOnClickListener{
            if(nombre.isEmpty() || curp.isEmpty()){
                ToastUtil.mostrarToast(requireContext(), "Curp y Nombre necesarios")
            }else{
            /*
                vm.registrarBeneficiario("Silvana", "ABCD", "2003-01-03",
                "Hombre", "Pachuca", "Valle Ceylan", "Tlanepantla")
             */
            }
        }

        // Clic en btnCondicion
        binding.btnCondicion.setOnClickListener {
            showDialog()
        }
        binding.fabCamara.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ) {
                startQRScanner()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    0
                )
            }
        }
    }

    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_sheet_layout)

        val btnListo = dialog.findViewById<ImageButton>(R.id.btnListoCondiciones)
        val chipGroup = dialog.findViewById<ChipGroup>(R.id.cgCondiciones)

        // Seleccionar chips previamente seleccionados
        for (chipId in selectedChipsSet) {
            val chip = chipGroup.findViewById<Chip>(chipId)
            chip?.isChecked = true
        }

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

                        // Agregar el chip seleccionado al conjunto de chips seleccionados
                        selectedChipsSet.add(chip.id)
                    } else {
                        // Si el chip no está seleccionado, quitarlo del conjunto
                        selectedChipsSet.remove(chip.id)
                    }
                }
            }
            // Imprimir todos los elementos del conjunto
            val selectedChipsString = selectedChipsSet.joinToString(", ")
            println("Elementos seleccionados: $selectedChipsString")

            val message: String = selectedChips.toString()
            binding.tvCondiciones.text = message
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
        // Spinner Sexo
        val genders = resources.getStringArray(R.array.generos)
        val adapterSexo = ArrayAdapter(requireContext(), R.layout.dropdown_item, genders)
        binding.spSexo.adapter = adapterSexo
        binding.spSexo.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
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

    private fun startQRScanner() {
        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Escanea un código QR")
        integrator.setOrientationLocked(true)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                binding.etCurpnRegistro.setText(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}