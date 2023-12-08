package com.example.minvest.MVVM.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyName(
    @PrimaryKey(autoGenerate = false)
    var symbol:String,
)