package com.example.menulateral.ApiAcces


import com.example.menulateral.DataModel.FaltaJustificada
import com.example.menulateral.DataModel.Alumno
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.DataModel.UserCep
import com.example.menulateral.DataModel.*
import retrofit2.Call
import retrofit2.http.*

interface ApiGets {

    companion object
    {
        val BASE_URL = "http://abp-politecnics.com/2023/dam01/"
    }

    @POST("api/Usuarios_CEP/email")
    fun getUsuarioCep(@Body email: String): Call<UserCep>

    @GET("api/Justificar_faltas/Validada/idAlumno/{idAlumno}/estadoFalta/{estadoFalta}")
    fun getFaltaJustificada(@Path("idAlumno") alumno: Int, @Path("estadoFalta") estadoFalta: Int): Call<MutableList<FaltaJustificada2>>
    
    @GET("api/Alumnoes/idUserCep/{idUserCep}")
    fun getAlumno(@Path("idUserCep") idUserCep: Int): Call<Alumno>

    @GET("api/Faltas/FaltasToShow/idAlumno/{idAlumno}")
    fun getFaltasToShow(@Path("idAlumno") idAlumno: Int): Call<List<FaltaToShow>>

    @GET("api/Faltas/FaltasToShow2/idAlumno/{idAlumno}/idJustificarFalta/{idJustificarFalta}")
    fun getFaltasToShow2(@Path("idAlumno") idAlumno: Int, @Path("idJustificarFalta") idJustificarFalta: Int): Call<List<FaltaToShow>>

    //update falta
    @POST("api/Faltas/SetFalta/idFalta/{idFalta}/idFaltaJustificada/{idFaltaJustificada}")
    fun updateFaltas(@Path("idFalta") idFalta: Int, @Path("idFaltaJustificada") idFaltaJustificada: Int?): Call<Void>

    @POST("api/Justificar_faltas/{Justificar_faltas}")
    fun createFaltaJustificada(@Body Justificar_faltas: FaltaJustificada): Call<Int>

    @GET("api/Faltas/FaltasToShowTotales/idAlumno/{idAlumno}")
    fun getFaltasToShowTotales(@Path("idAlumno") idAlumno: Int): Call<List<FaltaToShow>>

    @GET("api/Modulos/ModuloUf/idAlumno/{idAlumno}")
    fun getVisorAistencia(@Path("idAlumno") idAlumno: Int): Call<List<ModuloUFVisorAsistencia>>

    @GET("api/ClasesPersNoName/tutor_clase/{tutor_clase}")
    fun getClase(@Path("tutor_clase") tutor_clase: Int): Call<Int>

    @GET("api/Profesors/id_usuario_cep/{id_usuario_cep}")
    fun getProfesor(@Path("id_usuario_cep") id_usuario_cep: Int): Call<List<Profe>>

    @GET("api/Alumno/id_tutor/{id_tutor}")
    fun getAlumnosList(@Path("id_tutor") id_tutor: Int): Call<MutableList<Alumno>>

    @POST("api/JustificarFalta/SetEstado/id_justificar_falta/{id_justificar_falta}/estado_falta/{estado_falta}")
    fun updateJustificarFalta(@Path("id_justificar_falta") id_justificar_falta: Int, @Path("estado_falta") estado_falta: Int?): Call<Void>
    @GET("api/ClasesPers")
    fun getAllClasses(): Call<List<ClasePers>>

    @GET("api/Modulos/ModuloClase/idClase/{idClase}")
    fun getModulosClase(@Path("idClase") idClase: Int): Call<List<ModuloClase>>

    @GET("api/UFs/UfModulo/idModulo/{idModulo}")
    fun getUfModulo(@Path("idModulo") idModulo: Int): Call<List<UFModuloClase>>

    @GET("api/Alumno/idUf/{idUf}")
    fun getAlumnosUf(@Path("idUf") idUf: Int): Call<List<AlumnoUf>>

    @GET("api/Alumno/idProfe/{idProfe}")
    fun getAlumnosClase(@Path("idProfe") idProfe: Int): Call<MutableList<Alumno>>

    @GET("api/Clase/idProfe/{idProfe}")
    fun checkProfeTutor(@Path("idTutorClase") idProfe: Int): Call<Int>

    @GET("api/Justificar_faltas/Count/estadoFalta/{estadoFalta}")
    fun numFaltasPendientes(@Path("estadoFalta") estadoFalta: Int): Call<Int>
}