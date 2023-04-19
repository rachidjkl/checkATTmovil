package com.example.menulateral.ui.slideshow.extensiones

import android.graphics.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menulateral.DataModel.Alumno
import com.example.menulateral.R
import com.example.menulateral.databinding.FragmentSlideshowBinding
import com.example.menulateral.databinding.FragmentValidarFragmenExtensionABinding
import com.example.menulateral.ui.faltasJustificadas.FaltasJustificadasAdapter
import com.example.menulateral.ui.slideshow.SlideshowViewModel
import com.google.android.material.tabs.TabLayout


class ValidarFragmenExtensionA(val alumno: Alumno) : Fragment() {

    private var _binding: FragmentValidarFragmenExtensionABinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentValidarFragmenExtensionABinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.nombreTutor.text = alumno.nombreAlumno+" "+alumno.apellido1Alumno 

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tabs.setTabTextColors(Color.BLACK, resources.getColor(R.color.azul))
        binding.tabs.setSelectedTabIndicatorColor(resources.getColor(R.color.azul))
        binding.tabs.setBackgroundColor(resources.getColor(R.color.fondo))

        binding.tabs.addTab(binding.tabs.newTab().setText("Pendientes"))
        binding.tabs.addTab(binding.tabs.newTab().setText("Validadas"))
        binding.tabs.addTab(binding.tabs.newTab().setText("Rechazadas"))

        //Se muestra este tab seleccionado por defecto
        val defaultTab = binding.tabs.getTabAt(0)
        defaultTab?.select()

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                when (position) {
                    0 -> {

                    }
                    1 -> {

                    }
                    2 -> {

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}