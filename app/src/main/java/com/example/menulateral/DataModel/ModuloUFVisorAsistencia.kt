package com.example.menulateral.DataModel

import com.google.gson.annotations.SerializedName

class ModuloUFVisorAsistencia (@SerializedName("siglas_uf") var siglas_uf: String,
                               @SerializedName("nombre_modulo") var nombre_modulo: String,
                               @SerializedName("nombre_uf") var nombre_uf: String)