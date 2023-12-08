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
import com.example.minvest.MVVM.Network.Credentials
import com.example.minvest.MVVM.Network.Daum
import com.example.minvest.MVVM.Network.Service
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StockViewModel(var db: CompanyNameDB): ViewModel(){
    var listCompany = db.getCompanyNameDAO().getAllName()
    var count = mutableStateOf(0)
    init{
        viewModelScope.launch {
            db.getCompanyNameDAO().deleteTable()
            var sizeCompany = db.getCompanyNameDAO().getSize()
            if (sizeCompany == 0) {
                var cur = Service.companyName(Service.provideRetrofit()).getAllCompanyName();
                while(cur.isSuccessful==false){
                    Credentials.changeToken()
                    cur = Service.companyName(Service.provideRetrofit()).getAllCompanyName();
                }
                if(cur.isSuccessful){
                    var now = cur.body()
                    if(now != null){
                        now.data.forEach{
                            addToRoomDatabase(it)
                        }
                    }
                }
            }
        }
    }

    fun getCompanyName(it: Daum): CompanyName{
        var a = CompanyName(symbol = it.symbol, name = it.name, currency = it.currency, exchange = it.exchange, micCode = it.micCode, country = it.country, type = it.type, price = 0);
        return a
    }
    suspend fun addToRoomDatabase(it: Daum){
        db.getCompanyNameDAO().insertName(getCompanyName(it))
    }
    fun testingResponse() {
        viewModelScope.launch {
            var cur = Service.companyName(Service.provideRetrofit()).getAllCompanyName();
            while(cur.isSuccessful==false){
                Credentials.changeToken()
                cur = Service.companyName(Service.provideRetrofit()).getAllCompanyName();
            }
            if (cur.isSuccessful) {
                var now = cur.body()
                if (now != null) {
//                    now.data.forEach {
//                        addToRoomDatabase(it)
//                    }
                } else {
                    Log.d("loi", cur.code().toString() + "loi")
                }
            } else {
                Log.d("loi", cur.code().toString())
            }
        }
    }
}