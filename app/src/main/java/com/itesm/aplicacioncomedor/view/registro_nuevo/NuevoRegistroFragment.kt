package com.itesm.aplicacioncomedor.view.registro_nuevo

import android.Manifest
import android.app.AlertDialog
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
import androidx.lifecycle.Observer
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.zxing.integration.android.IntentIntegrator
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.databinding.FragmentNuevoRegistroBinding
import com.itesm.aplicacioncomedor.model.FechaaEdadCurp
import com.itesm.aplicacioncomedor.model.ToastUtil
import com.itesm.aplicacioncomedor.view.voluntario.DatePickerFragment
import com.itesm.aplicacioncomedor.viewmodel.AsistenciaVM
import com.itesm.aplicacioncomedor.viewmodel.RegistroNuevoVM


class NuevoRegistroFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentNuevoRegistroBinding? = null
    private val vmRegistroNuevo: RegistroNuevoVM by viewModels()
    private val vmAsistencia: AsistenciaVM by viewModels()

    // Camara


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val selectedChipsSet = HashSet<String>()       // Guarda el Id de los Chips para que cuando el BottomSheet vuelva a salir sigan achivos
    private val idChipsSet = HashSet<Int>()
    var edad: String = ""

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
        registrarObservadores()
    }

    private fun registrarObservadores() {
        vmAsistencia.asistenteEncontrado.observe(viewLifecycleOwner, Observer {encontrado ->
            if(encontrado){
                val diccionarioCondicion = mutableMapOf(
                    "Embarazada" to 1,
                    "Discapacidad Física" to 2,
                    "Discapacidad Intelectual" to 3,
                    "Discapacidad Visual" to 4,
                    "Discapacidad Auditiva" to 5,
                    "Inmigrante" to 6,
                    "Analfabeta" to 7,
                    "Tercera Edad" to 8,
                    "Otro" to 9)
                val benefiarioId = vmAsistencia.idAsistente.value
                for (element in selectedChipsSet) {
                    val valorSeleccionado = diccionarioCondicion[element]
                    if (benefiarioId != null && valorSeleccionado != null) {
                        vmRegistroNuevo.registrarCondicion(benefiarioId, valorSeleccionado)
                    }
                }
                binding.etCurpnRegistro.text?.clear()
                binding.etNombrenRegistro.text?.clear()
                binding.etMunicipioRegistro.text?.clear()
                binding.etCalleRegistro.text?.clear()
                binding.etColoniaRegistro.text?.clear()
                binding.tiFechaNacimientoNR.text.clear()
            } else {
                println("DIALOGO")
            }

        })
        vmRegistroNuevo.exitosoPost.observe(viewLifecycleOwner, Observer {exitoso ->
            if(exitoso){
                val nombre = binding.etNombrenRegistro.text.toString()
                val fecha = binding.tiFechaNacimientoNR.text.toString()
                if(edad == ""){
                    vmAsistencia.obtenerBeneficiario(nombre, fecha)
                }
                else{
                    vmAsistencia.obtenerBeneficiario(nombre, edad)
                }
                binding.etCurpnRegistro.text?.clear()
                binding.etNombrenRegistro.text?.clear()
                binding.etMunicipioRegistro.text?.clear()
                binding.etCalleRegistro.text?.clear()
                binding.etColoniaRegistro.text?.clear()
                binding.tiFechaNacimientoNR.text.clear()

            } else {
                println("Comprueba tu conexión")
            }
        })
    }

    private fun registrarEventos() {
        binding.btnFechaNR.setOnClickListener{
            showDatePickerDialog()
        }
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
                        val edadcurp = calculadoraEdad.convertirFormatoFechaYYToYYYY(curpChar)
                        binding.tiFechaNacimientoNR.setText(edadcurp.toString())
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
            val curp = binding.etCurpnRegistro.text.toString()
            val nombre = binding.etNombrenRegistro.text.toString()
            val municipio = binding.etMunicipioRegistro.text.toString()
            val colonia = binding.etColoniaRegistro.text.toString()
            val calle = binding.etCalleRegistro.text.toString()
            val sexo = binding.spSexo.selectedItem.toString()
            val fecha = binding.tiFechaNacimientoNR.text.toString()
            if(nombre.isEmpty() || curp.isEmpty()){
                mostrarDialogo("Datos incompletos")
            }else{
                if(edad == ""){
                    println("CURO: ${curp}")
                    println("Nombre: ${nombre}")
                    println("municipio: ${municipio}")
                    println("colonia: ${colonia}")
                    println("calle: ${calle}")
                    println("sexo: ${sexo}")
                    println("fecha: ${fecha}")
                    vmRegistroNuevo.registrarBeneficiario(nombre, curp, fecha,
                        sexo, calle, colonia, municipio)
                    mostrarDialogoExitoso("Registro Completado")
                }
                else{
                    println("CURO: ${curp}")
                    println("Nombre: ${nombre}")
                    println("municipio: ${municipio}")
                    println("colonia: ${colonia}")
                    println("calle: ${calle}")
                    println("sexo: ${sexo}")
                    println("fecha: ${edad}")
                    vmRegistroNuevo.registrarBeneficiario(nombre, curp, edad,
                        sexo, calle, colonia, municipio)
                    mostrarDialogoExitoso("Registro Completado")
                }

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
        for (chipId in idChipsSet) {
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
                        selectedChipsSet.add(chip.text.toString())
                        idChipsSet.add(chip.id)
                    } else {
                        // Si el chip no está seleccionado, quitarlo del conjunto
                        selectedChipsSet.remove(chip.text.toString())
                        idChipsSet.remove(chip.id)
                    }
                }
            }
            // Imprimir todos los elementos del conjunto
            val selectedChipsString = idChipsSet.joinToString(", ")
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
                // Divide el Qr en elementos
                val partes = dividirTexto(result.contents)
                // Curp
                binding.etCurpnRegistro.setText(partes[0])
                // Nombre
                val nombres = partes[4].split(" ").joinToString(" ") { it.toLowerCase().capitalize() }
                val apPaterno = partes[2].toLowerCase().capitalize()
                val apMaterno = partes[3].toLowerCase().capitalize()
                val nomCompleto = nombres + " " + apPaterno + " " + apMaterno
                binding.etNombrenRegistro.setText(nomCompleto)
                edad = convertirFormatoFecha(partes[6])
                println(edad)
                binding.tiFechaNacimientoNR.setText(edad)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
    // Para dividir el Qr en strings con el curp, nombre, etc
    fun dividirTexto(texto: String): List<String> {
        return texto.split("|")
    }
    fun convertirFormatoFecha(fechaOriginal: String): String {
        val partes = fechaOriginal.split("/")
        if (partes.size != 3) {
            return "Formato de fecha incorrecto"
        }

        val dia = partes[0]
        val mes = partes[1]
        val anio = partes[2]

        return "$anio-$mes-$dia"
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { dia, mes, anio -> onDateSelected(dia, mes, anio) }
        datePicker.show(childFragmentManager, "datePicker")
    }

    private fun onDateSelected(dia: Int, mes: Int, anio: Int) {
        val mesCorrecto= mes + 1
        binding.tiFechaNacimientoNR.setText("$anio-$mesCorrecto-$dia")
    }

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
}