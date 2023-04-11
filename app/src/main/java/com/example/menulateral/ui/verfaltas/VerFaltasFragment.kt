package com.example.menulateral.ui.verfaltas

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.DataModel.Faltas
import com.example.menulateral.DataModel.FaltasPorFecha
import com.example.menulateral.DataModel.Uf
import com.example.menulateral.databinding.FragmentJustificarFaltaBinding
import com.example.menulateral.databinding.FragmentVerFaltasBinding
import com.example.menulateral.ui.visorAsistencia.justificarFaltaAdapter
import java.time.LocalDate

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

    val faltasToShowList = listOf(
        FaltaToShow("2022-04-09 10:30:00", "10:30", "12:30", "MO1", "Unidad Formativa 1", 1),
        FaltaToShow("2022-04-09 10:30:00", "14:00", "16:00", "MO2", "Unidad Formativa 2", 2),
        FaltaToShow("2022-04-09 10:30:00", "09:00", "11:00", "MO3", "Unidad Formativa 1", 3),
        FaltaToShow("2022-05-09 10:30:00", "11:30", "13:30", "MO1", "Unidad Formativa 2", 4),
        FaltaToShow("2022-05-09 10:30:00", "14:00", "16:00", "MO2", "Unidad Formativa 1", 5),
        FaltaToShow("2022-05-09 10:30:00", "09:00", "11:00", "MO3", "Unidad Formativa 2", 6),
        FaltaToShow("2022-06-09 10:30:00", "10:00", "12:00", "MO3", "Unidad Formativa 1", 7),
        FaltaToShow("2022-06-09 10:30:00", "15:00", "17:00", "MO3", "Unidad Formativa 2", 8),
        FaltaToShow("2022-06-09 10:30:00", "17:30", "19:30", "MO1", "Unidad Formativa 1", 9)
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var faltasfecha = agruparFaltasPorFecha(faltasToShowList)

        val verFaltasViewModel =
            ViewModelProvider(this).get(VerFaltasViewModel::class.java)



        _binding = FragmentVerFaltasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = verFaltasAdapter(faltasfecha)
        binding.RecyclerViewVerFaltas.hasFixedSize()
        binding.RecyclerViewVerFaltas.layoutManager = LinearLayoutManager(this.context)
        binding.RecyclerViewVerFaltas.adapter = adapter


        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun agruparFaltasPorFecha(faltasToShowList: List<FaltaToShow>): List<FaltasPorFecha> {
        val faltasPorFecha = mutableMapOf<LocalDate, MutableList<FaltaToShow>>()

        for (falta in faltasToShowList) {
            val fecha = LocalDate.parse(falta.id_datetime.substringBefore(" "))

            if (fecha in faltasPorFecha) {
                faltasPorFecha[fecha]?.add(falta)
            } else {
                faltasPorFecha[fecha] = mutableListOf(falta)
            }
        }

        return faltasPorFecha.entries.map { FaltasPorFecha(it.key, it.value) }
    }



}