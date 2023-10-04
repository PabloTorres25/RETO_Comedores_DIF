package com.itesm.aplicacioncomedor.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.itesm.aplicacioncomedor.R
import com.itesm.aplicacioncomedor.databinding.FragmentFamiliaresRegistradosBinding
import com.itesm.aplicacioncomedor.viewmodel.registro_nuevo.FamiliaresRegistradosViewModel


class FamiliaresRegistrados : Fragment() {

    private lateinit var binding: FragmentFamiliaresRegistradosBinding  // Declara el objeto de vinculación
    private lateinit var viewModel: FamiliaresRegistradosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFamiliaresRegistradosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FamiliaresRegistradosViewModel::class.java)
        binding.fabFamiliaresRegistrados.setOnClickListener{
            findNavController().navigate(R.id.action_familiaresRegistrados_to_familiaFragment)
        }
    }

}