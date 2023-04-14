package com.example.menulateral.ui.visorAsistencia

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menulateral.ApiAcces.ApiGets
import com.example.menulateral.ApiAcces.RetrofitClient
import com.example.menulateral.DataModel.*
import com.example.menulateral.Login
import com.example.menulateral.databinding.FragmentVisorAsistenciaBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

class VisorAsistenciaFragment : Fragment() {

    private var _binding: FragmentVisorAsistenciaBinding? = null
    private var modulosUFVisorAsistenciaList: List<ModuloUFVisorAsistencia>? = null

    init {
        main()
    }
    fun main() = runBlocking {
        modulosUFVisorAsistenciaList = globalFun()
    }

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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var ufModulo = agruparUfPorModulo(modulosUFVisorAsistenciaList)

        _binding = FragmentVisorAsistenciaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val porcentajeAlumno = 84
        binding.progressBar.max = 100;
        binding.progressBar.setProgress(0)
        var cont = 0;
        val timer = object: CountDownTimer(15000, 5) {
            override fun onTick(millisUntilFinished: Long) {
                if (cont < porcentajeAlumno){
                    var aux = binding.textHome.text.toString()
                    binding.textHome.text = (aux.toInt() + 1).toString()
                    binding.progressBar.setProgress(binding.progressBar.progress +1)
                    cont ++
                }
            }
            override fun onFinish() {

            }
        }
        timer.start()

        val adapter = VisorAsistenciaAdapter(ufModulo)
        binding.RecyclerView.hasFixedSize()
        binding.RecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.RecyclerView.adapter = adapter

        return root
    }

    private fun agruparUfPorModulo(modulosUFVisorAsistenciaList: List<ModuloUFVisorAsistencia>?): List<UfConModulo> {
        val ufsConModulo = mutableMapOf<String, MutableList<ModuloUFVisorAsistencia>>()

        if (modulosUFVisorAsistenciaList != null){


            for (uf in modulosUFVisorAsistenciaList){
                val nombreModulo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    uf.siglas_uf + " - " + uf.nombre_modulo
                } else {
                    uf.siglas_uf + " - " + uf.nombre_modulo
                }

                if (nombreModulo in ufsConModulo) {
                    ufsConModulo[nombreModulo]?.add(uf)
                } else {
                    ufsConModulo[nombreModulo] = mutableListOf(uf)
                }
            }
        }
        return ufsConModulo.entries.map { UfConModulo(it.key, it.value) }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun globalFun(): List<ModuloUFVisorAsistencia>? {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.getVisorAistencia(Login.alumno.idAlumno)
            val response = call.execute()
            response.body()
        }.await()
    }


}

