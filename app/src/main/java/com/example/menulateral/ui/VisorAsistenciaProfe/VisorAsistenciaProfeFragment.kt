package com.example.menulateral.ui.VisorAsistenciaProfe

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menulateral.ApiAcces.ApiGets
import com.example.menulateral.ApiAcces.RetrofitClient
import com.example.menulateral.DataModel.*
import com.example.menulateral.databinding.FragmentVisorAsistenciaProfeBinding
import com.example.menulateral.ui.visorAsistencia.AdapterVisorAsistenciaProfe
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CompletableFuture

class VisorAsistenciaProfeFragment : Fragment() {

    private var _binding: FragmentVisorAsistenciaProfeBinding? = null
    private var allClases: List<ClasePers>? = null
    private var moduloClass: List<ModuloClase>? = null
    private var UfModuloClass: List<UFModuloClase>? = null
    private var alumnosUf: List<AlumnoUf>? = null


    init {
        main()
    }
    fun main() = runBlocking {
        allClases = globalFun()
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val visorAsistenciaProfeViewModel =
            ViewModelProvider(this).get(VisorAsistenciaProfeViewModel::class.java)

        _binding = FragmentVisorAsistenciaProfeBinding.inflate(inflater, container, false)
        val root: View = binding.root





        val spinnerClase = binding.autoCompleteTextView6
        val spinnerModulos = binding.autoCompleteTextView7
        val spinnerUf = binding.autoCompleteTextView8

        val defaultNombreModulo = binding.autoCompleteTextView7.text.toString()
        val defaultNombreUf = binding.autoCompleteTextView8.text.toString()



        val adapterClase = ClaseArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, allClases!!)
        spinnerClase.setAdapter(adapterClase)


        spinnerClase.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedClase = parent.getItemAtPosition(position) as ClasePers

            val idClase = selectedClase.idClase

            val nombreClaseTextView = binding.autoCompleteTextView6
            nombreClaseTextView.setText(selectedClase.nombreClase, false)

            val nombreModuloDefault = binding.autoCompleteTextView7
            nombreModuloDefault.setText(defaultNombreModulo, false)

            val nombreUfDefault = binding.autoCompleteTextView8
            nombreUfDefault.setText(defaultNombreUf, false)

            val apiGets = RetrofitClient.getInstance().create(ApiGets::class.java)
            GlobalScope.launch {
                val response = apiGets.getModulosClase(idClase).execute()
                if (response.isSuccessful) {
                    moduloClass = response.body()
                    val adapterModulosClase = SpinnerModulosAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, moduloClass!!)
                    activity?.runOnUiThread {
                        spinnerModulos.setAdapter(adapterModulosClase)
                    }
                } else {
                    Toast.makeText(context, "No modulos encontrados", Toast.LENGTH_SHORT).show()
                }
            }
        }

        spinnerModulos.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedModulo = parent.getItemAtPosition(position) as ModuloClase
            val idModulo = selectedModulo.idModulo


            val nombreModuloTextView = binding.autoCompleteTextView7
            nombreModuloTextView.setText(selectedModulo.siglasModulo + " - " + selectedModulo.nombreModulo, false)

            val nombreUfDefault = binding.autoCompleteTextView8
            nombreUfDefault.setText(defaultNombreUf, false)



            val apiGetsUf = RetrofitClient.getInstance().create(ApiGets::class.java)
            GlobalScope.launch {
                val response = apiGetsUf.getUfModulo(idModulo).execute()
                if (response.isSuccessful) {
                    UfModuloClass = response.body()
                    val adapterUfModulo = SpinnerUfModulo(requireContext(), android.R.layout.simple_dropdown_item_1line, UfModuloClass!!)
                    activity?.runOnUiThread {
                        spinnerUf.setAdapter(adapterUfModulo)
                    }
                } else {
                    Toast.makeText(context, "No UF encontradas", Toast.LENGTH_SHORT).show()
                }
            }

        }

        spinnerUf.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedUf = parent.getItemAtPosition(position) as UFModuloClase
            val idUf = selectedUf.idUf

            val nombreUfTextView = binding.autoCompleteTextView8
            nombreUfTextView.setText(selectedUf.nombreUf, false)

            val apiGetsAlumnos = RetrofitClient.getInstance().create(ApiGets::class.java)
            GlobalScope.launch {
                val response = apiGetsAlumnos.getAlumnosUf(idUf).execute()
                if (response.isSuccessful) {
                    alumnosUf = response.body()
                    val adapter = alumnosUf?.let { AdapterVisorAsistenciaProfe(it) }
                    activity?.runOnUiThread {
                        binding.RecyclerViewAlumnos.hasFixedSize()
                        binding.RecyclerViewAlumnos.layoutManager = LinearLayoutManager(context)
                        binding.RecyclerViewAlumnos.adapter = adapter


                        val porcentajeTotal = alumnosUf?.sumByDouble { it.porcentajeAsistencia.toDouble() }



                        binding.progressBar.max = 100;
                        binding.progressBar.setProgress(0)
                        var cont = 0;
                        val timer = object: CountDownTimer(15000, 5) {
                            override fun onTick(millisUntilFinished: Long) {
                                if (cont < porcentajeTotal!!){
                                    var aux = binding.text.text.toString()
                                    binding.text.text = (aux.toInt() + 1).toString()
                                    binding.progressBar.setProgress(binding.progressBar.progress +1)
                                    cont ++
                                }
                            }
                            override fun onFinish() {

                            }
                        }
                        timer.start()
                    }


                } else {
                  //  Toast.makeText(context, "No UF encontradas", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private suspend fun globalFun(): List<ClasePers>? {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.getAllClasses()
            val response = call.execute()
            response.body()
        }.await()
    }



}