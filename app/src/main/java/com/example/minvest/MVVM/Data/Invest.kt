package com.example.minvest.MVVM.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Invest(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name:String
)