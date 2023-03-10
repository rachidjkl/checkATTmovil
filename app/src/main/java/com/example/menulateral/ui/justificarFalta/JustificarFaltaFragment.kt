package com.example.menulateral.ui.justificarFalta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.menulateral.databinding.FragmentJustificarFaltaBinding

class JustificarFaltaFragment : Fragment() {

    private var _binding: FragmentJustificarFaltaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(JustificarFaltaViewModel::class.java)

        _binding = FragmentJustificarFaltaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textReason
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}