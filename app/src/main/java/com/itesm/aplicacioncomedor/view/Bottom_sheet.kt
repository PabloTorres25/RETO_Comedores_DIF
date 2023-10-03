package com.itesm.aplicacioncomedor.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.viewmodel.BottomSheetViewModel

class bottom_sheet : Fragment() {

    companion object {
        fun newInstance() = bottom_sheet()
    }

    private lateinit var viewModel: BottomSheetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BottomSheetViewModel::class.java)
        // TODO: Use the ViewModel
    }

}