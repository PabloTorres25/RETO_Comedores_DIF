package com.itesm.aplicacioncomedor.view.voluntario

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment (val listener: (day: Int, month: Int, year: Int) -> Unit):
    DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener(dayOfMonth, month, year)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val dia: Int = c.get(Calendar.DAY_OF_MONTH)
        val mes: Int = c.get(Calendar.MONTH)
        val anio: Int = c.get(Calendar.YEAR)
        val picker = DatePickerDialog(activity as Context, this, anio, mes, dia)
        return picker
    }

}