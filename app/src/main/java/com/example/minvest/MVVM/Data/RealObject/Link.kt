package com.example.minvest.MVVM.Data.RealObject

import com.example.minvest.MVVM.Data.CompanyName
import com.example.minvest.MVVM.Data.Invest
import com.example.minvest.MVVM.Data.Transaction

data class Link(
    var companyName: CompanyName,
    var transaction: Transaction,
    var invest: Invest,
)
