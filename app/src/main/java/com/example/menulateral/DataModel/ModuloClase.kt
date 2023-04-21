package com.example.menulateral.DataModel

import com.google.gson.annotations.SerializedName

data class ModuloClase (
    @SerializedName("siglas_uf") var siglasModulo: String,
    @SerializedName("nombre_modulo") var nombreModulo: String,
    @SerializedName("id_modulo") var idModulo: Int)