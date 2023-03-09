package com.example.menulateral.ui.verfaltas

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.menulateral.R
import com.example.menulateral.databinding.FragmentVerFaltasBinding

class VerFaltasFragment : Fragment() {

    private var _binding: FragmentVerFaltasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val verFaltasViewModel =
            ViewModelProvider(this).get(VerFaltasViewModel::class.java)

        _binding = FragmentVerFaltasBinding.inflate(inflater, container, false)

        val modulos = resources.getStringArray(R.array.spinner_options)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, modulos)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        val root: View = binding.root

        val textView: TextView = binding.textVerfaltas
        verFaltasViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}