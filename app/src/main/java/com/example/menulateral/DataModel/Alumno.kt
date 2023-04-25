package com.example.menulateral.DataModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Alumno (@SerializedName("id_alumno") var idAlumno: Int,
                   @SerializedName("dni_alumno") var dniAlumno: String,
                   @SerializedName("nombre_alumno") var nombreAlumno: String,
                   @SerializedName("apellido1_alumno") var apellido1Alumno: String): Serializable {
}


