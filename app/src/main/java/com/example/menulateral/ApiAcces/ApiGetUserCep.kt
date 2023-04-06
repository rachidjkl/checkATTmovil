package com.example.menulateral.ApiAcces


import retrofit2.Call
import retrofit2.http.GET

interface ApiGetUserCep {

    companion object
    {
        val BASE_URL = "https://localhost:44376/"
    }

    @GET("api/Usuarios_CEP")
    fun getUsuarioCep(): Call<List<String>>
}