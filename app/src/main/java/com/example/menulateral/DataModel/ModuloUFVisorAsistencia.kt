package com.example.menulateral.DataModel

import com.google.gson.annotations.SerializedName


class ModuloUFVisorAsistencia (@SerializedName("siglas_uf") var siglas_uf: String,
                               @SerializedName("nombre_modulo") var nombre_modulo: String,
                               @SerializedName("nombre_uf") var nombre_uf: String,
                               @SerializedName("listas_pasada_de_esa_uf") var listas_pasadas: Int,
                               @SerializedName("listas_pasadas_uf_alumno_presente") var listas_pasadas_presente: Int,
                               @SerializedName("porcentaje_asistencia") var porcentaje_asistencia: Float)