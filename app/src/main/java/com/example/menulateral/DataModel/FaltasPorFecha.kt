package com.example.menulateral.DataModel

import java.time.LocalDate

data class FaltasPorFecha(val fecha: LocalDate, val faltas: List<FaltaToShow>)