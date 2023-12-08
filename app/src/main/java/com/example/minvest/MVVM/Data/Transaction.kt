package com.example.minvest.MVVM.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var invest_id: Int,
    var company_id: Int,
    var quanity: Int,
    var money: Int
)