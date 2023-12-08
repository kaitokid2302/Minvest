package com.example.minvest.MVVM.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyInvest(
    @PrimaryKey(autoGenerate = false)
    var symbol: String,
    val name: String,
    val currency: String,
    val exchange: String,
    val micCode: String,
    val country: String,
    val type: String,
    val price: Float
)