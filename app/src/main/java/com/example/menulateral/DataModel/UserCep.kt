package com.example.menulateral.DataModel

import com.google.gson.annotations.SerializedName

data class UserCep (@SerializedName("id_usuario_cep") var idUserCep: Int,
                    @SerializedName("correo_cep") var correoCep: String,
                    @SerializedName("contra") var contraUserCep: String,
                    @SerializedName("tipo_usuario") var tipoUser: Int)

