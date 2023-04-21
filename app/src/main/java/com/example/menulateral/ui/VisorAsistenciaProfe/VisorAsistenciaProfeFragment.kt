package com.example.menulateral.ui.VisorAsistenciaProfe

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.menulateral.ApiAcces.ApiGets
import com.example.menulateral.ApiAcces.RetrofitClient
import com.example.menulateral.DataModel.ClasePers
import com.example.menulateral.DataModel.ModuloClase
import com.example.menulateral.DataModel.UFModuloClase
import com.example.menulateral.databinding.FragmentVisorAsistenciaProfeBinding
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


        val adapterClase = ClaseArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, allClases!!)
        spinnerClase.setAdapter(adapterClase)


        spinnerClase.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedClase = parent.getItemAtPosition(position) as ClasePers
            Toast.makeText(context, "ID del elemento seleccionado: ${selectedClase.idClase}", Toast.LENGTH_SHORT).show()
            val idClase = selectedClase.idClase

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

            Toast.makeText(context, "ID del elemento seleccionado: ${selectedModulo.idModulo}", Toast.LENGTH_SHORT).show()
            val idModulo = selectedModulo.idModulo

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