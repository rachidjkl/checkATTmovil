package com.example.menulateral.ui

import java.io.Serializable

class Faltas (val id_falta: Int,val  id_pasarLista: Int,val fecha:String, val  motivo:String, val documento_adjunto: String,  val comentario: String, val estado: Int):
    Serializable
