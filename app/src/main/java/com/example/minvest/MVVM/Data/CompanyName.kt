package com.example.minvest.MVVM.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyName(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var symbol: String,
    val name: String,
    val currency: String,
    val exchange: String,
    val micCode: String,
    val country: String,
    val type: String,
    var price: Double
)