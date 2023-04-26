package com.example.menulateral.ui.visorAsistencia

import android.annotation.SuppressLint
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


class VisorAsistenciaFragment : Fragment() {

    private var _binding: FragmentVisorAsistenciaBinding? = null
    private var modulosUFVisorAsistenciaList: List<ModuloUFVisorAsistencia>? = null
    private var nombreUsuario: String = ""
    private var porcentajeAsistenciaModulo: Float = 0.0F
    private var alumno: Alumno? = null


    fun main() = runBlocking {
        modulosUFVisorAsistenciaList = globalFun()
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        alumno = arguments?.getSerializable("alumno") as? Alumno
        if(alumno!=null){
            Login.alumno.idAlumno = alumno!!.idAlumno
        }
        main()


        _binding = FragmentVisorAsistenciaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        asignarNombre()

        // Crea un HashMap para agrupar los elementos por módulo
        val gruposPorModulo = HashMap<String, MutableList<ModuloUFVisorAsistencia>>()

        // Agrupa los elementos de la lista por módulo y filtra los elementos con porcentaje de asistencia igual a cero
        for (elemento in modulosUFVisorAsistenciaList!!.filter { it.listas_pasadas != 0 }) {
            if (!gruposPorModulo.containsKey(elemento.siglas_uf)) {
                gruposPorModulo[elemento.siglas_uf] = ArrayList()
            }
            gruposPorModulo[elemento.siglas_uf]?.add(elemento)
        }

        // Calcula el promedio del porcentaje de asistencia para cada grupo de elementos del mismo módulo
        val porcentajePorModulo = ArrayList<Float>()
        for ((_, elementos) in gruposPorModulo) {
            val sumPorcentaje = elementos.sumOf { it.porcentaje_asistencia.toDouble() }
            val promedioPorcentaje = (sumPorcentaje / elementos.size)
            porcentajePorModulo.add(promedioPorcentaje.toFloat())
        }




        var ufModulo = agruparUfPorModulo(modulosUFVisorAsistenciaList)


        val porcentajeAlumno = sumaTotal(porcentajePorModulo)
        binding.progressBar.max = 100
        binding.progressBar.setProgress(0)
        var cont = 0
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



    private fun agruparUfPorModulo(
        modulosUFVisorAsistenciaList: List<ModuloUFVisorAsistencia>?): List<UfConModulo> {
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


    private fun sumaTotal(porcentajePorModulo: ArrayList<Float>): Float {
        if (porcentajePorModulo.isEmpty()) {
            return 0f
        }
        var suma = 0f
        for (valor in porcentajePorModulo) {
            if(valor != 0.0f){
                suma += valor.toInt()
            }
        }
        return suma / porcentajePorModulo.size
    }

    @SuppressLint("SetTextI18n")
    private fun asignarNombre (){
        _binding?.textoNombreUsuario?.text = alumno?.nombreAlumno + " " + alumno?.apellido1Alumno
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun globalFun(): List<ModuloUFVisorAsistencia>? {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.getVisorAistencia(alumno!!.idAlumno)
            val response = call.execute()
            response.body()
        }.await()
    }





}