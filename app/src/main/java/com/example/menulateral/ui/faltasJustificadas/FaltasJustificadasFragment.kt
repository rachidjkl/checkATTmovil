package com.example.menulateral.ui.faltasJustificadas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.menulateral.databinding.FragmentFaltasJustificadasBinding

class FaltasJustificadasFragment : Fragment() {

    private var _binding: FragmentFaltasJustificadasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val faltasJustificadasViewModel =
            ViewModelProvider(this).get(FaltasJustificadasViewModel::class.java)

        _binding = FragmentFaltasJustificadasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSlideshow
        faltasJustificadasViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}