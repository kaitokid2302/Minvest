package com.example.minvest.MVVM

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minvest.MVVM.Data.CompanyName
import com.example.minvest.MVVM.Data.CompanyNameDB
import com.example.minvest.MVVM.Network.Credentials
import com.example.minvest.MVVM.Network.Daum
import com.example.minvest.MVVM.Network.Service
import kotlinx.coroutines.launch

class StockViewModel(var db: CompanyNameDB): ViewModel(){
    var listCompany = db.getCompanyNameDAO().getAllName()
    var price = mutableStateOf(0.0f)
    init{
        viewModelScope.launch {
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
                        now.data?.forEach{
                            addToRoomDatabase(it)
                        }
                    }
                }
            }
        }
    }

    fun getCompanyName(it: Daum): CompanyName{
        var a = CompanyName(symbol = it.symbol, name = it.name, currency = it.currency, exchange = it.exchange, micCode = it.micCode, country = it.country, type = it.type, price = 0.0f);
        return a
    }
    suspend fun addToRoomDatabase(it: Daum){
        db.getCompanyNameDAO().insertName(getCompanyName(it))
    }
    fun getPrice(symbol: String){
        viewModelScope.launch {
            var cur = Service.companyName(Service.provideRetrofit()).getPrice(symbol = symbol)
            while(cur.isSuccessful==false){
                Credentials.changeToken()
                cur = Service.companyName(Service.provideRetrofit()).getPrice(symbol = symbol)
            }
            Log.d("price", cur.body()?.price.toString())
            price.value = cur.body()!!.price!!.toFloat()
        }
    }
}