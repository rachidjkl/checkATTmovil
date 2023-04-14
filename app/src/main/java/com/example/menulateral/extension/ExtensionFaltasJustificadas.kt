package com.example.menulateral.extension

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menulateral.DataModel.*
import com.example.menulateral.databinding.FragmentExtensionFaltasJustificadasBinding
import com.example.menulateral.ui.visorAsistencia.AdapterExtensionFaltasJustificadas
import java.time.LocalDate


class extensionFaltasJustificadas(val faltasToShowList: MutableList<FaltaToShow>, val justificantes: FaltaJustificada) : Fragment() {

    private var _binding: FragmentExtensionFaltasJustificadasBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    val ufList = mutableListOf<Uf>(
        Uf(1, 101, 123, "UF1 - Introducción a la programación", "08", "09"),
        Uf(2, 102, 124, "UF2 - Programación orientada a objetos", "09", "10"),
        Uf(3, 103, 125, "UF3 - Estructuras de datos y algoritmos", "10", "11"),
        Uf(4, 101, 126, "UF4 - Bases de datos y SQL", "11", "12"),
        Uf(5, 102, 127, "UF5 - Desarrollo web con JavaScript", "12", "13"),
        Uf(6, 103, 128, "UF6 - Desarrollo móvil con Kotlin", "13", "14")
    )
    val moduloList = mutableListOf<Modulos>(
        Modulos(1, 101, "Introducción a la programación", "M01"),
        Modulos(2, 102, "Introducción a la programación", "M02"),
        Modulos(3, 103, "Introducción a la programación", "M03"),
        Modulos(4, 101, "Introducción a la programación", "M04"),
        Modulos(5, 102, "Introducción a la programación", "M05"),
        Modulos(6, 103, "Introducción a la programación", "M06")
    )


    override fun onCreateView
        (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExtensionFaltasJustificadasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.txtComentario.text = justificantes.comentarioFalta
        binding.txtMotivo.text = justificantes.motivoFalta

        var faltasfecha = agruparFaltasPorFecha(faltasToShowList)

        val adapter = AdapterExtensionFaltasJustificadas(faltasfecha)
        binding.RecyclerView.hasFixedSize()
        binding.RecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.RecyclerView.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun agruparFaltasPorFecha(faltasToShowList: MutableList<FaltaToShow>?): MutableList<FaltasPorFecha> {
        val faltasPorFecha = mutableMapOf<LocalDate, MutableList<FaltaToShow>>()

        if (faltasToShowList != null) {

            for (falta in faltasToShowList) {
                val fecha = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDate.parse(falta.id_datetime.substringBefore("T"))
                } else {
                    val dateParts = falta.id_datetime.substringBefore("T").split("-")
                    LocalDate.of(dateParts[0].toInt(), dateParts[1].toInt(), dateParts[2].toInt())
                }

                if (fecha in faltasPorFecha) {
                    faltasPorFecha[fecha]?.add(falta)
                } else {
                    faltasPorFecha[fecha] = mutableListOf(falta)
                }
            }
        }

        return faltasPorFecha.entries.map { FaltasPorFecha(it.key, it.value) } as MutableList<FaltasPorFecha>
    }

}