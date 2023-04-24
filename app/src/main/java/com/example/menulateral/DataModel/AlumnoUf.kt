package com.example.menulateral.DataModel

import com.google.gson.annotations.SerializedName

data class AlumnoUf(@SerializedName("id_alumno") var idAlumno: Int,
                   @SerializedName("nombre_alumno") var nombreAlumno: String,
                   @SerializedName("apellido1_alumno") var apellido1_alumno: String,
                   @SerializedName("apellido2_alumno") var apellido2_alumno: String,
                    @SerializedName("listasPasadasDeLaClaseTotales") var listasPasadasDeLaClaseTotales: Int,
                    @SerializedName("asistenciaDelAlumno") var asistenciaDelAlumno: Int,
                    @SerializedName("porcentajeAsistencia") var porcentajeAsistencia: Double)