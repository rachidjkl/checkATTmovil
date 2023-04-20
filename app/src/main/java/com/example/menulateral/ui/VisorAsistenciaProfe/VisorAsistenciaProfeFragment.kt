package com.example.menulateral.ui.VisorAsistenciaProfe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.menulateral.databinding.FragmentVisorAsistenciaProfeBinding

class VisorAsistenciaProfeFragment : Fragment() {

    private var _binding: FragmentVisorAsistenciaProfeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val visorAsistenciaProfeViewModel =
            ViewModelProvider(this).get(VisorAsistenciaProfeViewModel::class.java)

        _binding = FragmentVisorAsistenciaProfeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}