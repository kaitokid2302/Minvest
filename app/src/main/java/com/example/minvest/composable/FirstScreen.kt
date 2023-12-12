package com.example.minvest.composable

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.minvest.MVVM.Data.CompanyName
import com.example.minvest.MVVM.Data.RealObject.Link
import com.example.minvest.MVVM.SortBy
import com.example.minvest.MVVM.StockViewModel


@Composable
fun SimpleCompanyCard(stockViewModel: StockViewModel, modifier: Modifier, companyName: CompanyName){
    Card(modifier = modifier){
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()){
            Text("${companyName.name}", style = MaterialTheme.typography.titleMedium)
            Text("${companyName.symbol}", style = MaterialTheme.typography.bodySmall)
            // company price style = MaterialTheme.typography.bodySmall, bold
            Text("${companyName.price}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun DialogInfoStock(stockViewModel: StockViewModel, navController: NavController, companyName: CompanyName, onDissmiss: () -> Unit){
    var display by remember{
        mutableStateOf(true)
    }
    if(display) {
        Dialog(onDismissRequest = {
            display = false
            onDissmiss()
        }) {
            Card(modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(200.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer)
            ){
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()){
                    Text("${companyName.name}", style = MaterialTheme.typography.titleMedium, color = Color.White)
                    Text("${companyName.symbol}", style = MaterialTheme.typography.bodySmall, color = Color.White)
                    Text("Country: ${companyName.country}", style = MaterialTheme.typography.bodySmall, color = Color.White)
                    Text("Exchange: ${companyName.exchange}", style = MaterialTheme.typography.bodySmall, color = Color.White)
                    stockViewModel.getPrice(companyName = companyName)
                    var currentElement = stockViewModel.listCompany.find {
                        it == companyName
                    }
                    Text("Price: ${currentElement?.price ?: 0}", color = Color.White)
                    Button(onClick = {
                        display = false
                        onDissmiss()
                    }) {
                        Text("OK")

                    }
                }
            }
        }
    }
}

@Composable
fun RadioButtonSort(sortBy: SortBy, selected: SortBy?, changeSelected: () -> Unit, modifier: Modifier){
    Column(modifier = modifier){
        RadioButton(selected = sortBy==selected, onClick = {
            changeSelected()
        })
        when(sortBy){
            SortBy.byPriceASC ->{
                Row(){
                    Text("Price")
                    Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "up", tint = Color.Green)
                }

            }
            SortBy.byPriceDES ->{
                Row(){
                    Text("Price")
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "down", tint = Color.Red)
                }

            }
            SortBy.bySymbolASC -> {
                Row(){
                    Text("Symbol")
                    Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "up", tint = Color.Green)
                }
            }
            SortBy.bySymbolDES -> {
                Row(){
                    Text("Symbol")
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "down", tint = Color.Red)
                }
            }
            else -> {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchField(modifier: Modifier = Modifier.padding(5.dp), stockViewModel: StockViewModel, navController: NavController){
    var keyBoardController = LocalSoftwareKeyboardController.current
    Row(modifier = modifier){
        OutlinedTextField(
            value = stockViewModel.currenText,
            onValueChange = {
                stockViewModel.currenText = it
                stockViewModel.getCompanytoDisplay(stockViewModel.currenText)
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    keyBoardController?.hide()
                    stockViewModel.getCompanytoDisplay(stockViewModel.currenText)
                }
            ),
            singleLine = true
        )
        IconButton(onClick = {
            keyBoardController?.hide()
            stockViewModel.getCompanytoDisplay(stockViewModel.currenText)

        }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "search")
        }
    }
}

@Composable
fun FirstScreen(stockViewModel: StockViewModel, navController: NavController){
    var c = stockViewModel.currentCompanyName
    var selected by remember {
        mutableStateOf<SortBy?>(null)
    }
    var dialog by remember{
        mutableStateOf(false)
    }
    var companyChoose by remember{
        mutableStateOf<CompanyName?> (null)
    }
    Log.d("size", c.size.toString() + " ii " + stockViewModel.currentCompanyName.size.toString())
    Column(modifier = Modifier
        .padding(5.dp)
        .fillMaxSize()){
        SearchField(modifier = Modifier.align(Alignment.CenterHorizontally), stockViewModel = stockViewModel, navController = navController)
        LazyColumn(modifier = Modifier.height(ComposableSize.viewStockHeight.dp)){
            items(c){
                SimpleCompanyCard(stockViewModel = stockViewModel, companyName = it, modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .height(100.dp)
                    .clickable {
                        dialog = true
                        companyChoose = it
                    })
            }
        }
        if(dialog){
            DialogInfoStock(stockViewModel = stockViewModel, navController = navController, companyName = companyChoose!!, onDissmiss = {
                dialog = false
            })
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.padding(3.dp).fillMaxWidth().height(ComposableSize.viewRadioButonHeight.dp)){
            SortBy::class.sealedSubclasses.forEach {
                if (it.objectInstance!! is SortBy.byInterestAsc || it.objectInstance!! is SortBy.byInterestDes) {
                } else {
                    RadioButtonSort(it.objectInstance!!, selected, changeSelected = {
                        selected = it.objectInstance!!
                        stockViewModel.sortBy(selected)
                    }, modifier = Modifier.weight(1f))
                }
            }
        }
        BottomBar(stockViewModel = stockViewModel, navController = navController)
    }

}

