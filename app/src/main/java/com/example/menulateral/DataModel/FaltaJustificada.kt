package com.example.menulateral.DataModel

import com.google.gson.annotations.SerializedName

data class FaltaJustificada (@SerializedName("motivo_falta") var motivoFalta: String,
                             @SerializedName("documento_adj_falta") var documentoAdjFalta: String,
                             @SerializedName("comentario_falta") var comentarioFalta: String, //corresponden a siglas Modulo (MO8)
                             @SerializedName("estado_falta") var estadoFalta: Int)