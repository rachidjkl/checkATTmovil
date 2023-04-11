package com.example.menulateral.DataModel

import com.google.gson.annotations.SerializedName

data class Alumno (@SerializedName("id_alumno") var idAlumno: Int,
                   @SerializedName("id_clase") var idClase: Int,
                   @SerializedName("dni_alumno") var dniAlumno: String,
                   @SerializedName("nombre_alumno") var nombreAlumno: String,
                   @SerializedName("apellido1_alumno") var apellido1Alumno: String,
                   @SerializedName("apellido2_alumno") var apellido2Alumno: String,
                   @SerializedName("email_alumno") var emailAlumno: String,
                   @SerializedName("id_user_alumno_cep") var idUserAlumnoCep: Int,
                   @SerializedName("tel_alumno") var telAlumno: Int,
                   @SerializedName("nacimiento_alumno") var nacimientoAlumno: Int,
                   @SerializedName("incorp_alumno") var incorpAlumno: Int,
                   @SerializedName("direccion_alumno") var direccionAlumno: String,
                   @SerializedName("horas_cursadas_totales_alumno") var horasCursadasTotalesAlumno: Int,
                   @SerializedName("anyo_cursado_alumno") var anyoCursadoAlumno: Int)