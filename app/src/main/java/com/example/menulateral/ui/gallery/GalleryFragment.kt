package com.example.menulateral.ui.gallery

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menulateral.ApiAcces.ApiGets
import com.example.menulateral.ApiAcces.RetrofitClient
import com.example.menulateral.DataModel.Alumno
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.DataModel.Uf
import com.example.menulateral.Login
import com.example.menulateral.R
import com.example.menulateral.databinding.FragmentGalleryBinding
import com.example.menulateral.ui.visorAsistencia.VisorAsistenciaFragment
import com.example.menulateral.ui.visorAsistencia.justificarFaltaAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class GalleryFragment : Fragment() {

    private var alumnoList: MutableList<Alumno>? = null

    private var _binding: FragmentGalleryBinding? = null

    // LA CORRUTINA SE HA DE LLAMAR DESDE OTRA CORRUTINA
    init {
        main()
    }
    fun main() = runBlocking {
        alumnoList = getAlumnos()
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)




        val adapter = TuClaseAdapter(alumnoList, this)
        binding.RecyclerView.hasFixedSize()
        binding.RecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.RecyclerView.adapter = adapter



        binding.progressBar.max = 100;
        binding.progressBar.setProgress(0)
        var cont = 0;
        val timer = object: CountDownTimer(15000, 5) {
            override fun onTick(millisUntilFinished: Long) {
                if (cont < adapter.porcientoTotal/adapter.AlumnosList!!.size){
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

        adapter.setOnClickListener(){
            val alumno = alumnoList?.get(binding.RecyclerView.getChildAdapterPosition(it))
            val bundle = Bundle()
            bundle.putSerializable("alumno", alumno)
            val visorAsistencia = VisorAsistenciaFragment()
            visorAsistencia.arguments = bundle
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            // Agregar el fragmento actual a la pila de fragmentos
            fragmentTransaction.addToBackStack(null)

            // Reemplazar el fragmento actual con el FragmentListasPasadas2
            fragmentTransaction.replace(R.id.nav_host_fragment_content_main2, visorAsistencia)
            fragmentTransaction.commit()
        }


        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun getAlumnos(): MutableList<Alumno>? {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.getAlumnosClase(10005)
            val response = call.execute()
            response.body()
        }.await()
    }
}

private fun Bundle.putSerializable(s: String, grupo: Alumno?) {

}
