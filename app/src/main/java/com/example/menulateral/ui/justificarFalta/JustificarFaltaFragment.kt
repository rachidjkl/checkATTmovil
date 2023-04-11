package com.example.menulateral.ui.justificarFalta

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.DataModel.Faltas
import com.example.menulateral.DataModel.FaltasPorFecha
import com.example.menulateral.DataModel.Uf
import com.example.menulateral.databinding.FragmentJustificarFaltaBinding
import com.example.menulateral.ui.visorAsistencia.justificarFaltaAdapter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class JustificarFaltaFragment : Fragment() {

    private var _binding: FragmentJustificarFaltaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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



        val galleryViewModel =
            ViewModelProvider(this).get(JustificarFaltaViewModel::class.java)

        _binding = FragmentJustificarFaltaBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val adapter = justificarFaltaAdapter(faltasfecha)
        binding.RecyclerViewJustificarFalta.hasFixedSize()
        binding.RecyclerViewJustificarFalta.layoutManager = LinearLayoutManager(this.context)
        binding.RecyclerViewJustificarFalta.adapter = adapter

        val textView: TextView = binding.textReason
        return root







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



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
