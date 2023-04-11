package com.example.menulateral.ApiAcces


import com.example.menulateral.DataModel.UserCep
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiGets {

    companion object
    {
        val BASE_URL = "http://abp-politecnics.com/2023/dam01/api/"
    }

    @GET("Usuarios_CEP/email/{email}")
    fun getUsuarioCep(@Path("email") email: String): Call<UserCep>
}