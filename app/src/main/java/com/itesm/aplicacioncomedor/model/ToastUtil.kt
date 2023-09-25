package com.itesm.aplicacioncomedor.model

import android.content.Context
import android.widget.Toast

/**
 * Objeto de ayuda mostarToast
 * Sirve para crear un mensaje emergente, simple y util
 */

object ToastUtil {
    fun mostrarToast(context: Context, mensaje: String) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Como usarla
 * En la actividad o fragmento Kotlin que se requiera:
 * 1. Importarla
 *      import tec.pdtg.comedoresdif.model.ToastUtil
 * 2. Llamarla y usarla
 *      ToastUtil.mostrarToast(requireContext(), "Hola")
 */