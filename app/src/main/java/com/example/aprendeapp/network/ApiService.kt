package com.example.aprendeapp.network

import com.example.aprendeapp.model.Recurso
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("recursos")
    fun getAll(): Call<List<Recurso>>

    @GET("recursos/{id}")
    fun getById(@Path("id") id: Int): Call<Recurso>

    @POST("recursos")
    fun create(@Body recurso: Recurso): Call<Recurso>

    @PUT("recursos/{id}")
    fun update(@Path("id") id: Int, @Body recurso: Recurso): Call<Recurso>

    @DELETE("recursos/{id}")
    fun delete(@Path("id") id: Int): Call<Void>
}
