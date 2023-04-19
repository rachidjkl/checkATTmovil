package com.example.menulateral.extension

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.menulateral.R
import com.example.menulateral.databinding.FragmentExtensionFaltasJustificadasBinding
import com.example.menulateral.databinding.FragmentExtensionTuClaseBinding


class ExtensionTuClase : Fragment() {

    private var _binding: FragmentExtensionTuClaseBinding? = null

    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExtensionTuClaseBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}