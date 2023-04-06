package com.example.menulateral.ApiAcces


import retrofit2.Call
import retrofit2.http.GET

interface ApiGetUserCep {

    companion object
    {
        val BASE_URL = "https://192.168.1.167:44376/"
    }

    @GET("api/Usuarios_CEP")
    fun getUsuarioCep(): Call<List<String>>
}