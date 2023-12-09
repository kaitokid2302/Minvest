package com.example.minvest.MVVM

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minvest.MVVM.Data.CompanyName
import com.example.minvest.MVVM.Data.CompanyNameDB
import com.example.minvest.MVVM.Data.Invest
import com.example.minvest.MVVM.Data.RealObject.Link
import com.example.minvest.MVVM.Data.Transaction
import com.example.minvest.MVVM.Network.Credentials
import com.example.minvest.MVVM.Network.Daum
import com.example.minvest.MVVM.Network.Service
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class StockViewModel(var db: CompanyNameDB): ViewModel(){
    var _listCompany = db.getCompanyNameDAO().getAllName()
    var listCompany = mutableStateListOf<CompanyName>()
    lateinit var _invest: Flow<List<Invest>>
    lateinit var _transaction: Flow<List<Transaction>>
    var investList = mutableStateListOf<Invest>()

    /// for variable
    var onSearch by mutableStateOf(false)
    var currenText by mutableStateOf("")
    var currentCompanyName = mutableStateListOf<CompanyName>()
    var transactionList = mutableStateListOf<Link>()

    suspend fun getPriceBySymbol(symbol: String, companyName: CompanyName){
        viewModelScope.launch {
            var cur = Service.companyName(Service.provideRetrofit()).getPrice(symbol = symbol)
            while(cur.isSuccessful==false){
                Credentials.changeToken()
                cur = Service.companyName(Service.provideRetrofit()).getPrice(symbol = symbol)
            }
            Log.d("price", cur.body()?.price.toString())
            var price = cur.body()?.price?.toDouble()

            if (price != null) {
                companyName.price = price
            }

            db.getCompanyNameDAO().updatePrice(companyName = companyName)
        }
    }


    fun getPrice(companyName: CompanyName){
        viewModelScope.launch {
            getPriceBySymbol(companyName.symbol, companyName)
        }
    }

    fun getCompanytoDisplay(text:String){
        Log.d("text", text)
        viewModelScope.launch {
            var cur = db.getCompanyNameDAO().getCompanyCharacter(text).first()
            currentCompanyName.clear()
            Log.d("size", cur.size.toString())
           currentCompanyName.addAll(cur)
            Log.d("display", currentCompanyName.size.toString())
//            currentCompanyName = cur.toMutableList()
//            currentCompanyName = currentCompanyName
        }
    }
    suspend fun _init() {
        _invest = db.getInvest().allInvest()
        _transaction = db.getTransaction().allTransaction()
        viewModelScope.launch {
            _invest.collect {
                investList.addAll(it)
            }
        }
        Log.d("first", "first")
        viewModelScope.launch {
            _transaction.collect {transactions ->
                transactionList.clear()
                transactions.forEach {
                    var company = db.getCompanyNameDAO().getCompanyById(it.company_id)
                    var invest = db.getInvest().findInvest(it.invest_id)
                    transactionList.add(Link(companyName = company, invest = invest, transaction = it))
                }
            }
        }

        Log.d("second", "second")
        viewModelScope.launch {
            _listCompany.collect{
                listCompany.addAll(it)
            }
        }
        Log.d("third", "third")
    }

    fun newInvest(invest: Invest){
        viewModelScope.launch {
            db.getInvest().createNewInvest(invest)
        }
    }
    fun deleteInvest(invest: Invest){
        viewModelScope.launch {
            db.getInvest().deleteInvest(invest)
        }
    }
    fun newTransaction(transaction: Transaction){
        viewModelScope.launch {
            db.getTransaction().insertTransaction(transaction)
        }
    }
    fun deleteTransaction(transaction: Transaction){
        viewModelScope.launch {
            db.getTransaction().deleteTransaction(transaction)
        }
    }


    init{
        viewModelScope.launch {
//            db.getCompanyNameDAO().deleteTable()
            var sizeCompany = db.getCompanyNameDAO().getSize()
            viewModelScope.launch {
                _init()
            }
            if (sizeCompany == 0) {
                var cur = Service.companyName(Service.provideRetrofit()).getAllCompanyName();
                Log.d("init", "lamdeptrai")
//                for(i in 0..Credentials.tokenArray.size - 1){
//                    cur = Service.companyName(Service.provideRetrofit()).getAllCompanyName();
//                    Log.d("work", "${i}" + cur.code().toString())
//                    Credentials.changeToken()
//                }
                while(cur.code()!= 200){
                    Log.d("code", cur.code().toString() + " ${Credentials.cur}")
                    Credentials.changeToken()
                    cur = Service.companyName(Service.provideRetrofit()).getAllCompanyName();

                }
                Log.d("credential", Credentials.token)
                if(cur.isSuccessful){
                    var now = cur.body()
                    if(now != null){
                        var g = now!!
                        Log.d("size", g.data?.size.toString())
                        g.data?.forEach{
                            addToRoomDatabase(it)
                        }
                    }
                }
            }
        }
    }

    fun getCompanyName(it: Daum): CompanyName{
        var a = CompanyName(symbol = it.symbol, name = it.name, currency = it.currency, exchange = it.exchange, micCode = it.micCode, country = it.country, type = it.type, price = 0.0);
        return a
    }
    suspend fun addToRoomDatabase(it: Daum){
        Log.d("times", "x")
        db.getCompanyNameDAO().insertName(getCompanyName(it))
    }
    fun sortBy(_sortBy: SortBy?){
        viewModelScope.launch {
            when(_sortBy){
                SortBy.byPriceASC -> {
                    currentCompanyName.sortBy{it.price}
                }
                SortBy.byPriceDES -> {
                    currentCompanyName.sortByDescending { it.price }
                }
                SortBy.bySymbolASC -> {
                    currentCompanyName.sortBy { it.symbol }
                }
                SortBy.bySymbolDES -> {
                    currentCompanyName.sortByDescending { it.symbol }
                }

                else -> {

                }
            }
        }
    }
    fun formatTimestamp(time: Long): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        return sdf.format(time)
    }
    fun calculateInterest(link: Link): Double{
        var previousPrice = link.transaction.previousPrice
        var currentPrice = link.transaction.currentPrice
        var quantity = link.transaction.quanity

        return (currentPrice - previousPrice)*quantity
    }
}