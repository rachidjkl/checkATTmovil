package com.example.menulateral.ui.verfaltas

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menulateral.ApiAcces.ApiGets
import com.example.menulateral.ApiAcces.RetrofitClient
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.DataModel.Faltas
import com.example.menulateral.DataModel.FaltasPorFecha
import com.example.menulateral.DataModel.Uf
import com.example.menulateral.Login
import com.example.menulateral.databinding.FragmentJustificarFaltaBinding
import com.example.menulateral.databinding.FragmentVerFaltasBinding
import com.example.menulateral.ui.visorAsistencia.justificarFaltaAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.time.LocalDate


class VerFaltasFragment : Fragment() {


    private var _binding: FragmentVerFaltasBinding? = null

    private var faltasToShowListTotales: List<FaltaToShow>? = null

    // LA CORRUTINA SE HA DE LLAMAR DESDE OTRA CORRUTINA
    init {
        main()
    }
    fun main() = runBlocking {
        faltasToShowListTotales = globalFun()
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var faltasfecha = agruparFaltasPorFecha(faltasToShowListTotales)

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

    fun agruparFaltasPorFecha(faltasToShowListTotales: List<FaltaToShow>?): List<FaltasPorFecha> {
        val faltasPorFecha = mutableMapOf<LocalDate, MutableList<FaltaToShow>>()

        if (faltasToShowListTotales != null) {

            for (falta in faltasToShowListTotales) {
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
        return faltasPorFecha.entries.map { FaltasPorFecha(it.key, it.value) }
    }



    private suspend fun globalFun(): List<FaltaToShow>? {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.getFaltasToShowTotales(Login.alumno.idAlumno)
            val response = call.execute()
            response.body()
        }.await()
    }



}