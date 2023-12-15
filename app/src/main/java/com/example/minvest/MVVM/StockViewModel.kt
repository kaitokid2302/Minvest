package com.example.minvest.MVVM

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
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
import kotlinx.coroutines.runBlocking
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
    var currentSortByStock by mutableStateOf<SortBy?>(null)
    var currentSortByInterest by mutableStateOf<SortBy?>(null)
    var totalInterestTransactions = mutableStateOf<Double>(0.0)
    var currentInvest by mutableStateOf<Invest?>(null)
    var currentShowDialogOfBuyStockForInvest by mutableStateOf(false)
    var currentPrice by mutableStateOf(0.0)
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
            // sortBy(currentSortByStock)
        }
    }
// now

    fun getPrice(companyName: CompanyName){
        viewModelScope.launch {
            getPriceBySymbol(companyName.symbol, companyName)
            currentPrice = companyName.price
        }
    }
    //

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
            sortBy(currentSortByStock)
        }
    }
    suspend fun _init() {
        _invest = db.getInvest().allInvest()
        _transaction = db.getTransaction().allTransaction()
        viewModelScope.launch {

            _invest.collect {
                investList.clear()
                investList.addAll(it)
            }
        }
        Log.d("first", "first")
        viewModelScope.launch {
            _transaction.collect {transactions ->
                transactionList.clear()
                transactions.forEach {
                    viewModelScope.launch {
                        var company = db.getCompanyNameDAO().getCompanyById(it.company_id)
                        var invest = db.getInvest().findInvest(it.invest_id)
                        transactionList.add(Link(companyName = company, invest = invest, transaction = it))
                    }
                }
                sortBy(currentSortByInterest)
            }
        }

        Log.d("second", "second")
        viewModelScope.launch {
            _listCompany.collect{
                listCompany.clear()
                listCompany.addAll(it)
            }
        }
        Log.d("third", "third")
    }

    fun newInvest(name: String){
        viewModelScope.launch {
            db.getInvest().createNewInvest(Invest(name = name, interest = 0.0, date = System.currentTimeMillis()))
        }
    }
    fun deleteInvest(invest: Invest){
        viewModelScope.launch {
            db.getInvest().deleteInvest(invest)
        }
    }
    fun newTransaction(invest: Invest, companyName: CompanyName, quantity: Int){
        viewModelScope.launch {
            db.getTransaction().insertTransaction(Transaction(company_id = companyName.id, invest_id = invest.id, quanity = quantity, previousPrice = companyName.price, currentPrice = companyName.price, time = System.currentTimeMillis()))
        }
    }
    fun deleteTransaction(transaction: Transaction){
        viewModelScope.launch {
            totalInterestTransactions.value -= (transaction.currentPrice - transaction.previousPrice)*transaction.quanity
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
                while(cur.code()!= 200){
                    Log.d("code", cur.code().toString() + " ${Credentials.cur}")
                    Credentials.changeToken()
                    cur = Service.companyName(Service.provideRetrofit()).getAllCompanyName();
                }
                Log.d("credential", Credentials.token)
                if(cur.isSuccessful){
                    // a set of string
                    var set = mutableSetOf<String>()
                    var now = cur.body()
                    if(now != null){
                        var g = now!!
                        Log.d("size", g.data?.size.toString())
                        g.data?.forEach{
                            if(it.symbol !in set){
                                set.add(it.symbol)
                                addToRoomDatabase(it)
                            }
                        }
                    }
                    currentCompanyName.clear()
                    currentCompanyName.addAll(db.getCompanyNameDAO().getAllName().first())
                }
            }
            else{
                totalInterestTransactions.value = 0.0
                var allInvest = db.getInvest().allInvest().first()
                allInvest.forEach {
                    totalInterestTransactions.value += it.interest
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
            var newCurrentCompanyName = currentCompanyName.toMutableList()
            when(_sortBy){
                SortBy.byPriceASC -> {
                    currentSortByStock = SortBy.byPriceASC
                    newCurrentCompanyName.sortBy{it.price}
                    currentCompanyName = newCurrentCompanyName.toMutableStateList()
                }
                SortBy.byPriceDES -> {
                    currentSortByStock = SortBy.byPriceDES
                    newCurrentCompanyName.sortByDescending { it.price }
                    currentCompanyName = newCurrentCompanyName.toMutableStateList()
                }
                SortBy.bySymbolASC -> {
                    currentSortByStock = SortBy.bySymbolASC
                    newCurrentCompanyName.sortBy { it.symbol }
                    currentCompanyName = newCurrentCompanyName.toMutableStateList()
                }
                SortBy.bySymbolDES -> {
                    currentSortByStock = SortBy.bySymbolDES
                    newCurrentCompanyName.sortByDescending { it.symbol }
                    currentCompanyName = newCurrentCompanyName.toMutableStateList()
                }

                SortBy.byInterestAsc -> {
                    currentSortByInterest = SortBy.byInterestAsc
                    transactionList.sortBy {
                        calculateInterest(it)
                    }
                }
                SortBy.byInterestDes -> {
                    currentSortByInterest = SortBy.byInterestDes
                    transactionList.sortByDescending {
                        calculateInterest(it)
                    }
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
    fun resetAllTransactionPrice(){
        viewModelScope.launch {
            /// newInvest is investList copy
            var newInvest = investList.toMutableList()
            newInvest.forEach {
                it.interest = 0.0
            }
            var _newTransactionList = transactionList.toMutableList()
            // newTransactionList = _newTransactionList copy but get the transactions
            var newTransactionList = _newTransactionList.map {
                it.transaction
            }
            var newOfTransactions = mutableListOf<Transaction>()
            totalInterestTransactions.value = 0.0
            newTransactionList.forEach {
                var company = db.getCompanyNameDAO().getCompanyById(it.company_id)
                var invest = db.getInvest().findInvest(it.invest_id)
                var price = Service.companyName(Service.provideRetrofit()).getPrice(symbol = company.symbol)
                while(price.isSuccessful==false){
                    Credentials.changeToken()
                    price = Service.companyName(Service.provideRetrofit()).getPrice(symbol = company.symbol)
                 }
                // create newIt exactily the same copy of it
                var newIt = it.copy()
                newIt.currentPrice = price.body()?.price?.toDouble() ?: 0.0
                newOfTransactions.add(newIt)
                // find the invest that has the same id with newIt from newInvest
                var cur = newInvest.find {invest ->  invest.id == newIt.invest_id}
                // cur.interest += calculateInterest(Link(companyName = company, invest = invest, transaction = newIt))
                // safe update cur?.interest = cur?.interest + calculateInterest(Link(companyName = company, invest = invest, transaction = newIt))
                cur?.interest = cur?.interest?.plus(calculateInterest(Link(companyName = company, invest = invest, transaction = newIt)))!!
                totalInterestTransactions.value += calculateInterest(Link(companyName = company, invest = invest, transaction = newIt))
            }
            db.getInvest().insertAllInvest(newInvest)
            db.getTransaction().insertAlTransaction(newOfTransactions)
        }
    }
    fun convertToNumber(text: String): Int{
        var res = 0
        for(i in 0..text.length - 1){
            if(text[i] in '0'..'9'){
                res = res*10 + (text[i] - '0')
            }
        }
        return res
    }
    fun fourDecimalDigit(number: Double): Double{
        return (number*1000).toInt().toDouble()/1000
    }
}