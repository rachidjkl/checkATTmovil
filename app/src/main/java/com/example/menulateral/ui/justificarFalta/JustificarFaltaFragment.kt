package com.example.menulateral.ui.justificarFalta

import android.R
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menulateral.ApiAcces.ApiGets
import com.example.menulateral.ApiAcces.RetrofitClient
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.DataModel.Faltas
import com.example.menulateral.DataModel.FaltasPorFecha
import com.example.menulateral.DataModel.Uf
import com.example.menulateral.databinding.FragmentJustificarFaltaBinding
import com.example.menulateral.ui.visorAsistencia.justificarFaltaAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class JustificarFaltaFragment : Fragment() {

    companion object var selectedFaltas = mutableListOf<FaltaToShow>()
    private var _binding: FragmentJustificarFaltaBinding? = null
    private var faltasToShowList: List<FaltaToShow>? = null

    init {
        main()
    }

    fun main() = runBlocking {
        faltasToShowList = globalFun()
    }

    private var date: Date = getCurrentDateTime()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    val faltasToShowList1 = listOf(
        FaltaToShow("2005-03-25T00:05:00", "10:30", "12:30", "MO1", "Unidad Formativa 1", 1),
        FaltaToShow("2023-04-13T08:18:30.523Z", "14:00", "16:00", "MO2", "Unidad Formativa 2", 2),
        FaltaToShow("2022-04-09 10:30:00", "09:00", "11:00", "MO3", "Unidad Formativa 1", 3),
        FaltaToShow("2022-05-09 10:30:00", "11:30", "13:30", "MO1", "Unidad Formativa 2", 4),
        FaltaToShow("2022-05-09 10:30:00", "14:00", "16:00", "MO2", "Unidad Formativa 1", 5),
        FaltaToShow("2022-05-09 10:30:00", "09:00", "11:00", "MO3", "Unidad Formativa 2", 6),
        FaltaToShow("2022-06-09 10:30:00", "10:00", "12:00", "MO3", "Unidad Formativa 1", 7),
        FaltaToShow("2022-06-09 10:30:00", "15:00", "17:00", "MO3", "Unidad Formativa 2", 8),
        FaltaToShow("2022-06-09 10:30:00", "17:30", "19:30", "MO1", "Unidad Formativa 1", 9)
    )




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

        binding.btnEnviar.setOnClickListener(){


            UFCheckBoxAdapter.selectedFaltas


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
            val call = userCepApi.getFaltasToShow()
            val response = call.execute()
            response.body()
        }.await()
    }


    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
