package com.example.api_rest

import android.widget.TextView
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("/Offres")
    suspend fun getOffres():Response<MutableList<offre>>

    @DELETE("/Offres/{id}")
    suspend fun deleteOffre(@Path("id") id: String)

    @POST("/Offres")
    suspend fun createOffre(@Body offre: offre)

}