package com.example.menulateral.ui.verfaltas

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menulateral.Faltas
import com.example.menulateral.R
import com.example.menulateral.Uf
import com.example.menulateral.databinding.FragmentVerFaltasBinding
import com.example.menulateral.ui.faltasJustificadas.FaltasJustificadasAdapter
import com.example.menulateral.ui.visorAsistencia.justificarFaltaAdapter

val faltasList = mutableListOf<Faltas>(
    Faltas(1, 101, 1, "Medico"),
    Faltas(2, 102, 1, "Trabajo"),
    Faltas(3, 103, 1, "Trabajo"),
    Faltas(4, 101, 2, "Examen onducir"),
    Faltas(5, 102, 2, "Bar"),
    Faltas(6, 103, 2, "Sida")
)

val ufList = mutableListOf<Uf>(
    Uf(1, 101, 123, "UF1 - Introducción a la programación", "08", "09"),
    Uf(2, 102, 124, "UF2 - Programación orientada a objetos", "09", "10"),
    Uf(3, 103, 125, "UF3 - Estructuras de datos y algoritmos", "10", "11"),
    Uf(4, 101, 126, "UF4 - Bases de datos y SQL", "11", "12"),
    Uf(5, 102, 127, "UF5 - Desarrollo web con JavaScript", "12", "13"),
    Uf(6, 103, 128, "UF6 - Desarrollo móvil con Kotlin", "13", "14")
)

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


        val root: View = binding.root


        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}