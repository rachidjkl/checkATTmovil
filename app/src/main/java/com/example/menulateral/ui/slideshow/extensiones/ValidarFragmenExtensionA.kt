package com.example.menulateral.ui.slideshow.extensiones

import android.graphics.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menulateral.ApiAcces.ApiGets
import com.example.menulateral.ApiAcces.RetrofitClient
import com.example.menulateral.DataModel.Alumno
import com.example.menulateral.DataModel.FaltaJustificada2
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.R
import com.example.menulateral.databinding.FragmentValidarFragmenExtensionABinding
import com.example.menulateral.ui.slideshow.AdapterValidarJustificanteA
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class ValidarFragmenExtensionA(val alumno: Alumno) : Fragment() {

    private var _binding: FragmentValidarFragmenExtensionABinding? = null
    private val binding get() = _binding!!
    private var faltaJustificadaListValidada: MutableList<FaltaJustificada2>? = null
    private var faltaJustificadaListPendiente: MutableList<FaltaJustificada2>? = null
    private var faltaJustificadaListRechazada: MutableList<FaltaJustificada2>? = null

    init {
        main()
    }

    fun main() = runBlocking {
        faltaJustificadaListValidada = cargarListFaltaJustificada(alumno.idAlumno, 1)
        faltaJustificadaListPendiente = cargarListFaltaJustificada(alumno.idAlumno, 0)
        faltaJustificadaListRechazada = cargarListFaltaJustificada(alumno.idAlumno, -1)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun onItemClick(position: Int, infoFalta: MutableList<FaltaToShow>, infoJustificante: MutableList<FaltaJustificada2>) {

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
                        if(faltaJustificadaListPendiente != null){
                            binding.RecyclerView.visibility = View.VISIBLE
                            val adapter = AdapterValidarJustificanteA(this@ValidarFragmenExtensionA, faltaJustificadaListPendiente, 0)
                            binding.RecyclerView.hasFixedSize()
                            binding.RecyclerView.layoutManager = LinearLayoutManager(context)
                            binding.RecyclerView.adapter = adapter
                        }else{
                            binding.RecyclerView.visibility = View.GONE
                        }

                    }
                    1 -> {
                        if(faltaJustificadaListValidada != null){
                            binding.RecyclerView.visibility = View.VISIBLE
                            val adapter = AdapterValidarJustificanteA(this@ValidarFragmenExtensionA, faltaJustificadaListValidada, 1)
                            binding.RecyclerView.hasFixedSize()
                            binding.RecyclerView.layoutManager = LinearLayoutManager(context)
                            binding.RecyclerView.adapter = adapter
                        }else{
                            binding.RecyclerView.visibility = View.GONE
                        }

                    }
                    2 -> {
                        if(faltaJustificadaListRechazada != null){
                            binding.RecyclerView.visibility = View.VISIBLE
                            val adapter = AdapterValidarJustificanteA(this@ValidarFragmenExtensionA, faltaJustificadaListRechazada, -1)
                            binding.RecyclerView.hasFixedSize()
                            binding.RecyclerView.layoutManager = LinearLayoutManager(context)
                            binding.RecyclerView.adapter = adapter
                        }else{
                            binding.RecyclerView.visibility = View.GONE
                        }

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }


    suspend fun cargarListFaltaJustificada(alumno : Int, estado : Int):MutableList<FaltaJustificada2>?{

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.getFaltaJustificada(alumno,estado)
            val response = call.execute()
            response.body()
        }.await()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}