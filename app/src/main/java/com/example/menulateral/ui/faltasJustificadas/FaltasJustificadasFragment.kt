package com.example.menulateral.ui.faltasJustificadas

import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menulateral.ApiAcces.ApiGets
import com.example.menulateral.ApiAcces.RetrofitClient
import com.example.menulateral.DataModel.Faltas
import com.example.menulateral.DataModel.FaltaJustificada
import com.example.menulateral.DataModel.FaltaJustificada2
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.Login
import com.example.menulateral.R
import com.example.menulateral.databinding.FragmentFaltasJustificadasBinding
import com.example.menulateral.extension.extensionFaltasJustificadas
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class FaltasJustificadasFragment : Fragment() {

    private var _binding: FragmentFaltasJustificadasBinding? = null
    private var faltaJustificadaListValidada: MutableList<FaltaJustificada2>? = null
    private var faltaJustificadaListPendiente: MutableList<FaltaJustificada2>? = null
    private var faltaJustificadaListRechazada: MutableList<FaltaJustificada2>? = null

    init {
        main()
    }

    fun main() = runBlocking {
        faltaJustificadaListValidada = cargarListFaltaJustificada(Login.alumno.idAlumno, 1)
        faltaJustificadaListPendiente = cargarListFaltaJustificada(Login.alumno.idAlumno, 0)
        faltaJustificadaListRechazada = cargarListFaltaJustificada(Login.alumno.idAlumno, -1)
    }


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    fun onItemClick(position: Int, infoFalta: MutableList<FaltaToShow>, infoJustificante: MutableList<FaltaJustificada2>) {
        //-------------------------------------------------Cambiar fragment-------------------------------------------//
        val fragmentoPasarLista = extensionFaltasJustificadas(infoFalta, infoJustificante.get(position))
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        // Agregar el fragmento actual a la pila de fragmentos
        // Reemplazar el fragmento actual con el FragmentNuevo
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragmentoPasarLista)
        fragmentTransaction.commit()

        //------------------------------------------------------------------------------------------------------------//
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val faltasJustificadasViewModel =
            ViewModelProvider(this).get(FaltasJustificadasViewModel::class.java)

        _binding = FragmentFaltasJustificadasBinding.inflate(inflater, container, false)


        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        if(faltaJustificadaListPendiente != null){
            binding.RecyclerView.visibility = View.VISIBLE
            val adapter = FaltasJustificadasAdapter(this@FaltasJustificadasFragment, faltaJustificadaListPendiente, 0)
            binding.RecyclerView.hasFixedSize()
            binding.RecyclerView.layoutManager = LinearLayoutManager(context)
            binding.RecyclerView.adapter = adapter
            binding.noTienesFoto.visibility = View.GONE
        }else{
            binding.noTienesFoto.visibility = View.VISIBLE
            binding.RecyclerView.visibility = View.GONE
        }

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.alumno_ideal) // Cargar imagen desde drawable
        val diameter = 200 // Diámetro del círculo

        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, diameter, diameter, true) // Escalar imagen al tamaño del círculo

        val circleBitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888) // Crear nuevo bitmap para el círculo
        val canvas = Canvas(circleBitmap) // Dibujar en el nuevo bitmap
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = Color.WHITE
        canvas.drawCircle(diameter / 2f, diameter / 2f, diameter / 2f, paint) // Dibujar círculo blanco
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(scaledBitmap, 0f, 0f, paint) // Dibujar imagen en el círculo

        binding.imageView.setImageBitmap(circleBitmap) // Mostrar el bitmap del círculo en el ImageView

        binding.tabs.setTabTextColors(Color.BLACK, resources.getColor(R.color.azul))
        binding.tabs.setSelectedTabIndicatorColor(resources.getColor(R.color.azul))
        binding.tabs.setBackgroundColor(resources.getColor(R.color.fondo))

        binding.tabs.addTab(binding.tabs.newTab().setText("Pendientes"))
        binding.tabs.addTab(binding.tabs.newTab().setText("Validadas"))
        binding.tabs.addTab(binding.tabs.newTab().setText("Rechazada"))

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
                            val adapter = FaltasJustificadasAdapter(this@FaltasJustificadasFragment, faltaJustificadaListPendiente, 0)
                            binding.RecyclerView.hasFixedSize()
                            binding.RecyclerView.layoutManager = LinearLayoutManager(context)
                            binding.RecyclerView.adapter = adapter
                            binding.noTienesFoto.visibility = View.GONE
                        }else{
                            binding.noTienesFoto.visibility = View.VISIBLE
                            binding.RecyclerView.visibility = View.GONE
                        }

                    }
                    1 -> {
                        if(faltaJustificadaListValidada != null){
                            binding.RecyclerView.visibility = View.VISIBLE
                            val adapter = FaltasJustificadasAdapter(this@FaltasJustificadasFragment, faltaJustificadaListValidada, 1)
                            binding.RecyclerView.hasFixedSize()
                            binding.RecyclerView.layoutManager = LinearLayoutManager(context)
                            binding.RecyclerView.adapter = adapter
                            binding.noTienesFoto.visibility = View.GONE
                        }else{
                            binding.noTienesFoto.visibility = View.VISIBLE
                            binding.RecyclerView.visibility = View.GONE
                        }
                    }
                    2 -> {
                        if(faltaJustificadaListRechazada != null){
                            binding.RecyclerView.visibility = View.VISIBLE
                            val adapter = FaltasJustificadasAdapter(this@FaltasJustificadasFragment, faltaJustificadaListRechazada, -1)
                            binding.RecyclerView.hasFixedSize()
                            binding.RecyclerView.layoutManager = LinearLayoutManager(context)
                            binding.RecyclerView.adapter = adapter
                            binding.noTienesFoto.visibility = View.GONE
                        }else{
                            binding.noTienesFoto.visibility = View.VISIBLE
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
