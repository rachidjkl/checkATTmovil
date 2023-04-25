package com.example.menulateral.ui.slideshow.extensiones

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menulateral.ApiAcces.ApiGets
import com.example.menulateral.ApiAcces.RetrofitClient
import com.example.menulateral.DataModel.FaltaJustificada2
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.DataModel.FaltasPorFecha
import com.example.menulateral.R
import com.example.menulateral.databinding.FragmentExtensionFaltasJustificadasBinding
import com.example.menulateral.databinding.FragmentValidarFragmenExtensionBBinding
import com.example.menulateral.ui.visorAsistencia.AdapterExtensionFaltasJustificadas
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate

class ValidarFragmenExtensionB(val faltasToShowList: MutableList<FaltaToShow>, val justificantes: FaltaJustificada2) : Fragment() {


    private var _binding: FragmentValidarFragmenExtensionBBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView
                (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentValidarFragmenExtensionBBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.txtComentario.text = justificantes.comentarioFalta
        binding.txtMotivo.text = justificantes.motivoFalta

        var faltasfecha = agruparFaltasPorFecha(faltasToShowList)

        val adapter = AdapterExtensionFaltasJustificadas(faltasfecha)
        binding.RecyclerView.hasFixedSize()
        binding.RecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.RecyclerView.adapter = adapter

        binding.buttonValidar.setOnClickListener(){
            updateApi(justificantes.idJustificarFaltas, 1)
            Toast.makeText(context,"mmmmmmmmmmgggggggg",Toast.LENGTH_SHORT).show()
        }
        binding.buttonValidar.setOnClickListener(){
            updateApi(justificantes.idJustificarFaltas, -1)
        }

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

    fun updateApi(id_justificar_falta: Int, estado_falta: Int) {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        GlobalScope.launch() {
            val call = userCepApi.updateJustificarFalta(id_justificar_falta, estado_falta)
            val response = call.execute()
        }
    }
}