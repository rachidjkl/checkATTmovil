package com.example.menulateral.DataModel

import com.google.gson.annotations.SerializedName

data class FaltaToShow (@SerializedName("id_datetime") var id_datetime: String, // formatog date time
                        @SerializedName("hora_inicio") var hora_inicio: String,
                        @SerializedName("id_justificar_falta") var idJustificarFalta: Int,
                        @SerializedName("hora_fin") var hora_fin: String,
                        @SerializedName("siglas_uf") var siglas_uf: String, //corresponden a siglas Modulo (MO8)
                        @SerializedName("nombre_uf") var nombreUf: String,
                        @SerializedName("id_falta") var idFalta: Int)