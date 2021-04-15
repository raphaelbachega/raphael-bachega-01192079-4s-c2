package com.example.cachorros

import retrofit2.Call
import retrofit2.http.*

interface ApiCachorros {

    @GET("cachorros")
    fun get():Call<List<Cachorro>>

    @GET("cachorro/{id}")
    fun get(@Path("id")id:Int):Call<Cachorro>

}