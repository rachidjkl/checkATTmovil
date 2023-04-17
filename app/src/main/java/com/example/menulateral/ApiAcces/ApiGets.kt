package com.example.menulateral.ApiAcces


import com.example.menulateral.DataModel.FaltaJustificada
import com.example.menulateral.DataModel.Alumno
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.DataModel.UserCep
import retrofit2.Call
import retrofit2.http.*

interface ApiGets {

    companion object
    {
        val BASE_URL = "http://abp-politecnics.com/2023/dam01/"
    }

    @GET("api/Usuarios_CEP/email/{email}")
    fun getUsuarioCep(@Path("email") email: String): Call<UserCep>


    @GET("api/Justificar_faltas/Validada/idAlumno/{idAlumno}/estadoFalta/{estadoFalta}")
    fun getFaltaJustificada(@Path("idAlumno") alumno: Int, @Path("estadoFalta") estadoFalta: Int): Call<MutableList<FaltaJustificada>>
    
    @GET("api/Alumnoes/idUserCep/{idUserCep}")
    fun getAlumno(@Path("idUserCep") idUserCep: Int): Call<Alumno>

    @GET("api/Faltas/FaltasToShow/idAlumno/{idAlumno}")
    fun getFaltasToShow(@Path("idAlumno") idAlumno: Int): Call<List<FaltaToShow>>

    @GET("api/Faltas/FaltasToShow2/idAlumno/{idAlumno}/idJustificarFalta/{idJustificarFalta}")
    fun getFaltasToShow2(@Path("idAlumno") idAlumno: Int, @Path("idJustificarFalta") idJustificarFalta: Int): Call<List<FaltaToShow>>

    @POST("api/Faltas/SetFalta/idFalta/{idFalta}/idFaltaJustificada/{idFaltaJustificada}")
    fun updateFaltas(@Path("idFalta") idFalta: Int, @Path("idFaltaJustificada") idFaltaJustificada: Int?): Call<Void>

    @POST("api/Justificar_faltas/{Justificar_faltas}")
    fun createFaltaJustificada(@Body Justificar_faltas: FaltaJustificada): Call<Int>
}