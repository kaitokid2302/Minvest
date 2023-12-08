package com.example.minvest.MVVM

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minvest.MVVM.Data.CompanyName
import com.example.minvest.MVVM.Data.CompanyNameDB
import com.example.minvest.MVVM.Network.Company
import com.example.minvest.MVVM.Network.Service
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StockViewModel(var db: CompanyNameDB): ViewModel(){
    var listCompany = db.getCompanyNameDAO().getAllName()
    var count = mutableStateOf(0)
    init{
        viewModelScope.launch {
//            db.getCompanyNameDAO().deleteTable()
            var sizeCompany = db.getCompanyNameDAO().getSize()
            if (sizeCompany == 0) {
                try {
                    var cur = Service.companyName(Service.provideRetrofit()).getAllCompanyName()
                    var res = cur.data
                    var count = 0
                    res.forEach{
                        addToRoomDatabase(CompanyName(symbol = it.symbol))
                    }

                } catch (e: Exception) {
                    Log.d("lamdeptrai", e.message.toString())
                }
            }
        }
    }
    suspend fun addToRoomDatabase(company: CompanyName){
        db.getCompanyNameDAO().insertName(company)
    }
}