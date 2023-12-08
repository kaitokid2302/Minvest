package com.example.minvest.MVVM.Network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CompanyAPI {
    @GET("stocks")
    suspend fun getAllCompanyName(@Header("X-RapidAPI-Key") token: String = Credentials.token, @Header("X-RapidAPI-Host") xRapidApiHost: String = Credentials.host, @Query("exchange") exchange: String = "NASDAQ", @Query("format") format: String = "json"): Response<Company>
    @GET("price")
    suspend fun getPrice(@Header("X-RapidAPI-Key") token: String = Credentials.token, @Header("X-RapidAPI-Host") xRapidApiHost: String = Credentials.host, @Query("symbol") symbol: String): Response<Company>
}