package com.example.menulateral.DataModel

import com.google.gson.annotations.SerializedName

data class UFModuloClase (@SerializedName("id_uf") var idUf: Int,
                          @SerializedName("nombre_uf") var nombreUf: String)