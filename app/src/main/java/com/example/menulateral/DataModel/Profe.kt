package com.example.menulateral.DataModel

import com.google.gson.annotations.SerializedName

data class Profe (@SerializedName("id_profe") var idProfe: Int,
                  @SerializedName("nombre_completo") var nombreProfe: String,
                  @SerializedName("correo_cep") var correoProfe: String,
                   @SerializedName("dni_profe") var dniProfe: String,
                   @SerializedName("nombre_profe") var nombreProfe: String,
                   @SerializedName("apellido1_profe") var apellido1Profe: String) {
}


