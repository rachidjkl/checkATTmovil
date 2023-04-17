package com.example.menulateral.DataModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FaltaJustificada (@SerializedName("id_justificar_falta") var idJustificarFaltas: Int, // formatog date time
                        @SerializedName("motivo_falta") var motivoFalta: String,
                        @SerializedName("documento_adj_falta") var documentoAdjFalta: String,
                        @SerializedName("comentario_falta") var comentarioFalta: String, //corresponden a siglas Modulo (MO8)
                        @SerializedName("estado_falta") var estadoFalta: Int)
