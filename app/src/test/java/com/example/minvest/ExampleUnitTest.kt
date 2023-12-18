package com.example.minvest

import android.util.Log
import com.example.minvest.MVVM.Network.Credentials
import com.example.minvest.MVVM.Network.Service
import okhttp3.OkHttpClient
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.Request

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun callAPI() {
        var cur = Service.companyName(Service.provideRetrofit())._getPrice(symbol = "AAPL")
        while(cur.isSuccessful==false){
            Credentials.changeToken()
            cur = Service.companyName(Service.provideRetrofit())._getPrice(symbol = "AAPL")
        }
        // asert price is not null
        assert(cur.body()?.price!=null)
    }
    fun call10APIandFilter(){
        for(i in 1..10){
            var cur = Service.companyName(Service.provideRetrofit())._getPrice(symbol = "AAPL")
            while(cur.isSuccessful==false){
                Credentials.changeToken()
                cur = Service.companyName(Service.provideRetrofit())._getPrice(symbol = "AAPL")
            }
            // asert price is not null
            assert(cur.body()?.price!=null)
        }
    }
    fun findMaximumPrice(){
        var max = 0.0
        var maxSymbol = ""
        for(i in 1..10){
            var cur = Service.companyName(Service.provideRetrofit())._getPrice(symbol = "AAPL")
            while(cur.isSuccessful==false){
                Credentials.changeToken()
                cur = Service.companyName(Service.provideRetrofit())._getPrice(symbol = "AAPL")
            }
            // asert price is not null
            assert(cur.body()?.price!=null)
            if(cur.body()?.price!!.toDouble() > max){
                max = cur.body()?.price!!.toDouble()
                maxSymbol = cur.body()?.status.toString()
            }
        }
        assert(max > 0.0)
    }

}