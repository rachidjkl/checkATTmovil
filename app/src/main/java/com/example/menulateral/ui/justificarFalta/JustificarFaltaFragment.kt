package com.example.menulateral.ui.justificarFalta

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menulateral.ApiAcces.ApiGets
import com.example.menulateral.ApiAcces.RetrofitClient
import com.example.menulateral.DataModel.FaltaJustificada
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.DataModel.FaltasPorFecha
import com.example.menulateral.Login
import com.example.menulateral.R
import com.example.menulateral.databinding.FragmentJustificarFaltaBinding
import com.example.menulateral.extension.extensionFaltasJustificadas
import com.example.menulateral.ui.visorAsistencia.VisorAsistenciaFragment
import com.example.menulateral.ui.visorAsistencia.justificarFaltaAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import android.content.ContentResolver
import java.util.*


class JustificarFaltaFragment : Fragment() {

    companion object var selectedFaltas = mutableListOf<FaltaToShow>()
    private var _binding: FragmentJustificarFaltaBinding? = null
    private var faltasToShowList: List<FaltaToShow>? = null
    private var updateExit: Boolean = true
    private var idFaltaJustificada: Int = -1

    //select imgatge
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var editText: EditText


    // LA CORRUTINA SE HA DE LLAMAR DESDE OTRA CORRUTINA
    init {
        main()
    }


    fun main() = runBlocking {
        faltasToShowList = globalFun()
    }

    fun callCreateFaltaApi(faltaJustificada: FaltaJustificada) = runBlocking {

        idFaltaJustificada = createFaltaJustificada(faltaJustificada)!!.toInt()
    }


    private var date: Date = getCurrentDateTime()
    private val binding get() = _binding!!


    // This property is only valid between onCreateView and
    // onDestroyView.



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var faltasfecha = agruparFaltasPorFecha(faltasToShowList)



        val galleryViewModel =
            ViewModelProvider(this).get(JustificarFaltaViewModel::class.java)

        _binding = FragmentJustificarFaltaBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val adapter = justificarFaltaAdapter(faltasfecha)
        binding.RecyclerViewJustificarFalta.hasFixedSize()
        binding.RecyclerViewJustificarFalta.layoutManager = LinearLayoutManager(this.context)
        binding.RecyclerViewJustificarFalta.adapter = adapter

        val textView: TextView = binding.textReason

        val cal = Calendar.getInstance()

            // Obtiene la fecha actual
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)

        // Crea un objeto Calendar para la fecha actual
        val currentCalendar = Calendar.getInstance()
        currentCalendar.set(year, month, dayOfMonth)

        // Obtiene el nombre del día de la semana correspondiente a la fecha actual
        val currentDayOfWeek = currentCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())

        binding.datePickerButton.text = "${dayOfMonth}/${month + 1}/${year}"




            // seleccionar imagen
        binding.editAdjuntarDocumento.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PICK_IMAGE_REQUEST)
        }


        binding.btnEnviar.setOnClickListener(){
            _binding!!.editReason.text.toString()

            var faltaJustificada: FaltaJustificada = FaltaJustificada(_binding!!.editReason.text.toString(),_binding!!.editAdjuntarDocumento.text.toString()
                                                                        ,_binding!!.editComentario.text.toString(),0)


            //llamamos a la corrutina que llamará a la api
            callCreateFaltaApi(faltaJustificada)

            FaltasAgrupadasAdapter.selectedFaltas.forEach {
                updateApi(it.idFalta, idFaltaJustificada)
            }
            if (!updateExit){
                Toast.makeText(requireActivity(), "Error al hacer Update", Toast.LENGTH_SHORT).show()
                updateExit == true
            }else{
                Toast.makeText(requireActivity(), "Falta Justificada Enviada", Toast.LENGTH_SHORT).show()
                FaltasAgrupadasAdapter.selectedFaltas.clear()

            }

            val fragment = VisorAsistenciaFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            // Agregar el fragmento actual a la pila de fragmentos
            // Reemplazar el fragmento actual con el FragmentNuevo
            fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment)
            fragmentTransaction.commit()


        }

        binding.datePickerButton.setOnClickListener {

            val datePicker = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    // Aquí se ejecutará el código cuando el usuario seleccione una fecha
                    val selectedDate = "${selectedDayOfMonth}/${selectedMonth + 1}/${selectedYear}"

                    // Crea un objeto Calendar para la fecha seleccionada
                    val selectedCalendar = Calendar.getInstance()
                    selectedCalendar.set(selectedYear, selectedMonth, selectedDayOfMonth)

                    // Obtiene el nombre del día de la semana correspondiente a la fecha seleccionada
                    val selectedDayOfWeek = selectedCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())

                    binding.datePickerButton.text = selectedDate

                },
                year, month, dayOfMonth
            )

            datePicker.show()
        }
        return root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val uri = data.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DISPLAY_NAME)
            val cursor = requireActivity().contentResolver.query(uri!!, filePathColumn, null, null, null)
            cursor?.moveToFirst()
            val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
            val fileName = columnIndex?.let { cursor.getString(it) }
           binding.editAdjuntarDocumento.setText(fileName)
            cursor?.close()
        }
    }


    fun agruparFaltasPorFecha(faltasToShowList: List<FaltaToShow>?): List<FaltasPorFecha> {
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

        return faltasPorFecha.entries.map { FaltasPorFecha(it.key, it.value) }
    }






    private suspend fun globalFun(): List<FaltaToShow>? {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.getFaltasToShow(Login.alumno.idAlumno)
            val response = call.execute()
            response.body()
        }.await()
    }

     private suspend fun createFaltaJustificada(faltaJustificada: FaltaJustificada):Int?{

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)
         return GlobalScope.async {
            val call = userCepApi.createFaltaJustificada(faltaJustificada)
            val response = call.execute()
             response.body()

        }.await()

    }


     fun updateApi(idFalta: Int, idFaltaJustificada: Int) {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        GlobalScope.launch() {
            val call = userCepApi.updateFaltas(idFalta, idFaltaJustificada)
            val response = call.execute()

            if (response.code() != 204){
                updateExit = false
            }else{

            }
        }
     }


    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
