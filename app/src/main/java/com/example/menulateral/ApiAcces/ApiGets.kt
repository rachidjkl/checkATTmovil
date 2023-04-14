package com.example.menulateral.ApiAcces


import com.example.menulateral.DataModel.FaltaJustificada
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.DataModel.UserCep
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiGets {

    companion object
    {
        val BASE_URL = "http://abp-politecnics.com/2023/dam01/"
    }

    @GET("api/Usuarios_CEP/email/{email}")
    fun getUsuarioCep(@Path("email") email: String): Call<UserCep>

<<<<<<< HEAD
    @GET("api/Faltas/FaltasToShow")
    fun getFaltasToShow(): Call<List<FaltaToShow>>

    @GET("api/Justificar_faltas/Validada/idAlumno/{idAlumno}/estadoFalta/{estadoFalta}")
    fun getFaltaJustificada(@Path("idAlumno") alumno: Int, @Path("estadoFalta") estadoFalta: Int): Call<MutableList<FaltaJustificada>>
=======
    @GET("api/Faltas/FaltasToShow/idAlumno/{idAlumno}")
    fun getFaltasToShow(@Path("idAlumno") idAlumno: Int): Call<List<FaltaToShow>>
>>>>>>> origin/JoelBranch1
}