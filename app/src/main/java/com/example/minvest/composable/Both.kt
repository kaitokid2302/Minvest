package com.example.minvest.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.minvest.MVVM.Data.CompanyName
import com.example.minvest.MVVM.StockViewModel

@Composable
fun SimpleCompanyCard(stockViewModel: StockViewModel, modifier: Modifier, companyName: CompanyName){
    Card(modifier = modifier){
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()){
            Text("${companyName.name}", style = MaterialTheme.typography.titleMedium)
            Text("${companyName.symbol}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun DialogInfoStock(stockViewModel: StockViewModel, navController: NavController, companyName: CompanyName){
    var display by remember{
        mutableStateOf(true)
    }
    if(display) {
        Dialog(onDismissRequest = {
            display = false
        }) {
            Card(modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(200.dp)){
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()){
                    Text("${companyName.name}", style = MaterialTheme.typography.titleMedium)
                    Text("${companyName.symbol}", style = MaterialTheme.typography.bodySmall)
                    Text("Country: ${companyName.country}", style = MaterialTheme.typography.bodySmall)
                    Text("Exchange: ${companyName.exchange}", style = MaterialTheme.typography.bodySmall)
                    stockViewModel.getPrice(companyName = companyName)
                    var currentElement = stockViewModel.listCompany.find {
                        it == companyName
                    }
                    Text("Price: ${currentElement?.price ?: 0}")
                    Button(onClick = { display = false}) {
                        Text("OK")
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBar(stockViewModel: StockViewModel, navController: NavController){
    Row(modifier = Modifier.fillMaxWidth()){
        Button(onClick = { navController.navigate("First Screen") },
            modifier = Modifier.weight(1f)) {
            Text("Stocks")
        }
        Button(onClick = { navController.navigate("Second Screen") },
            modifier = Modifier.weight(1f)) {
            Text("My invests")
        }
    }
}