package com.example.minvest.MVVM.Network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Company(
    val data: List<Daum>,
    val status: String,
)

data class Daum(
    val symbol: String,
    val name: String,
    val currency: String,
    val exchange: String,
    @Json(name = "mic_code")
    val micCode: String,
    val country: String,
    val type: String,
)
