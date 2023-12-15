package com.example.minvest.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.placeholder
import com.example.minvest.MVVM.Data.CompanyName
import com.example.minvest.MVVM.Data.CompanyNameDB
import com.example.minvest.MVVM.Data.Invest
import com.example.minvest.MVVM.Data.RealObject.Link
import com.example.minvest.MVVM.Data.Transaction
import com.example.minvest.MVVM.SortBy
import com.example.minvest.MVVM.StockViewModel
import java.time.format.TextStyle



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyStockDialog(stockViewModel: StockViewModel, navController: NavController, companyName: CompanyName, invest: Invest, onDismiss: () -> Unit){
    var showDialog by remember { mutableStateOf(true) }
    var quantity by remember { mutableStateOf("") }
    var total  by remember { mutableStateOf(0.0) }
    var price by remember { mutableStateOf(0.0) }
    if(showDialog){
        stockViewModel.getPrice(companyName)
        var currentElement = stockViewModel.listCompany.find {
            it == companyName
        }
        price = currentElement!!.price
        Dialog(onDismissRequest = {
            showDialog = false
            onDismiss()
        }) {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondaryContainer), modifier = Modifier
                .padding(3.dp)
                .height(190.dp)){
                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                    /// text of price
                    /// text of quantity*price(total)
                    /// outline text field of quantity
                    /// button of buy
                    /// button of cancel

                    /// text of price
                    // text of company name
                    Text("${companyName.name}", style = MaterialTheme.typography.titleMedium, color = Color.White)
                    // text of company symbol
                    Text("${companyName.symbol}", style = MaterialTheme.typography.bodySmall, color = Color.White)

                    Text("${stockViewModel.fourDecimalDigit(price)}", style = MaterialTheme.typography.titleMedium, color = Color.White)
                    // text of quantity*price(total)

                    total = stockViewModel.convertToNumber(quantity) * price
                    total = stockViewModel.fourDecimalDigit(total)
                    Text("Total: " + total.toString(), style = MaterialTheme.typography.bodySmall, color = Color.White)


                    /// outline text field of quantity, only number are allow
                    OutlinedTextField(
                        value = quantity,
                        onValueChange = {
                            quantity = it
                            // Cập nhật giá trị chỉ khi nhập vào là số
                        },
                        label = { Text("Quantity") },
                        placeholder = { Text("Quantity") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White,
                            placeholderColor = Color.White,
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            disabledBorderColor = Color.White
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                // Cập nhật giá trị khi nhấn nút done
                                stockViewModel.newTransaction(invest = invest, companyName = companyName, quantity = quantity.toInt())
                                showDialog = false
                                onDismiss()
                            }
                        ),
                        maxLines = 1
                    )
                    // a row of buy and cancel button
                    Row(modifier = Modifier.padding(3.dp)) {
                        Button(onClick = {

                            // buy stock
                            stockViewModel.newTransaction(invest = invest, companyName = companyName, quantity = quantity.toInt())
                            showDialog = false
                            onDismiss()
                        }) {
                            Text("Buy")
                        }
                        Button(onClick = {
                            showDialog = false
                            onDismiss()
                        }) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CompanyCard(stockViewModel: StockViewModel, navController: NavController, companyName: CompanyName, invest: Invest, onSelected: () -> Unit){
    Card(modifier = Modifier
        .padding(5.dp)
        .fillMaxSize()
        .height(110.dp)
        .clickable {
            stockViewModel.currentShowDialogOfBuyStockForInvest = true
            stockViewModel.currentInvest = invest
            onSelected()
        }){
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()){
            Text("${companyName.name}", style = MaterialTheme.typography.titleMedium)
            Text("${companyName.symbol}", style = MaterialTheme.typography.bodySmall)
            // company price style = MaterialTheme.typography.bodySmall, bold
            Text("${companyName.price}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ListOfStock(stockViewModel: StockViewModel, navController: NavController){

    var selected by remember{
        mutableStateOf<SortBy?> (null)
    }
    var currentSelectedCompany by remember{
        mutableStateOf<CompanyName?>(null)
    }
    var isSelected by remember{
        mutableStateOf(false)
    }
    Column(modifier = Modifier
        .padding(5.dp)
        .fillMaxSize()){
        Button(onClick = {navController.popBackStack()}) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
        }
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            SearchField(stockViewModel = stockViewModel, navController = navController)
        }
        LazyColumn(modifier = Modifier
            .padding(5.dp)
            .height(ComposableSize.viewStockHeight.dp)){
            items(stockViewModel.currentCompanyName)
            {
                CompanyCard(stockViewModel = stockViewModel, navController = navController, companyName = it, invest = stockViewModel.currentInvest!!){
                    currentSelectedCompany = it
                    isSelected = true
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(ComposableSize.viewRadioButonHeight.dp)){
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
        Button(onClick = {navController.popBackStack()}, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("OK")
        }
        if(isSelected){
            BuyStockDialog(stockViewModel = stockViewModel, navController = navController, companyName = currentSelectedCompany!!, invest = stockViewModel.currentInvest!!, onDismiss = {isSelected = false})
        }
    }

}

@Composable
fun SimpleTransactionCard(stockViewModel: StockViewModel, navController: NavController, link: Link) {
    var interest = stockViewModel.calculateInterest(link)
    Card(
        modifier = Modifier
            .padding(2.dp)
            .height(120.dp),
        // color for this card
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // text of interest, small typography, bold
            // text of date, small typography
            // text of name of transaction of link
            Text(
                text = "${link.companyName.name}",
                style = MaterialTheme.typography.bodySmall
            )
            // text of symbol
            Text(
                text = "${link.companyName.symbol}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Red
            )
            Text(
                text = "Total interest $interest",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
            Text(
                text = "${stockViewModel.formatTimestamp(link.transaction.time)}",
                style = MaterialTheme.typography.bodySmall
            )
            // quantity: small typography, bold
            Text(
                text = "Quantity: " + "${link.transaction.quanity}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Buying price: " + "${link.transaction.previousPrice}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Price now: " + "${link.transaction.currentPrice}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun DetailOfInvest(stockViewModel: StockViewModel, navController: NavController, invest: Invest, onDismiss: () -> Unit){

    var showDialog by remember { mutableStateOf(true) }
    if(showDialog){
        Dialog(onDismissRequest = {
            showDialog = false
            onDismiss()
        }) {
            Card(modifier = Modifier
                .padding(3.dp)
                .height(500.dp)
                .width(600.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondaryContainer)){
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier
                    .padding(7.dp)
                    .fillMaxSize()){
                    // Invest Name: Medium typography, bold
                    // date: small typography
                    // interest: small typography, bold
                    Text("${invest.name}", style = MaterialTheme.typography.titleMedium, color = Color.White)
                    var date = stockViewModel.formatTimestamp(invest.date)
                    Text("$date", style = MaterialTheme.typography.bodySmall,  color = Color.White)
                    Text("${stockViewModel.fourDecimalDigit(invest.interest)}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold,  color = Color.White)
                    // LazyVerticalGrid 3 column of transaction, belong to this invest: modifier = Modifier.padding(2.dp).weight(1f)
                    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier
                        .padding(2.dp)
                        .weight(1f)){
                        val filterList = stockViewModel.transactionList.filter {
                            it.invest == invest
                        }
                        items(filterList){
                            if(it.invest==invest){
                                SimpleTransactionCard(stockViewModel = stockViewModel, navController = navController, link = it)
                            }
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                        Button(onClick = {
                            showDialog = false
                            onDismiss()
                        }) {
                            Text("OK")
                        }
                        Button(onClick = {
                            showDialog = false
                            onDismiss()
                            navController.navigate("List Of Stock")
                        }) {
                            Text("Buy Stocks")
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun SimpleCardOfInvest(stockViewModel: StockViewModel, navController: NavController, invest: Invest, onClick: () -> Unit){
    var showDetail by remember { mutableStateOf(false) }
    Card(modifier = Modifier
        .padding(3.dp)
        .height(120.dp)
        ){
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()){
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()){
                // Invest Name: Medium typography, bold
                // date: small typography
                // interest: small typography, bold
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier
                    .weight(1f)
                    .clickable {
                        showDetail = true
                    }){
                    Text("${invest.name}", style = MaterialTheme.typography.titleMedium)
                    var date = stockViewModel.formatTimestamp(invest.date)
                    Text("$date", style = MaterialTheme.typography.bodySmall)
                    Text("${stockViewModel.fourDecimalDigit(invest.interest)}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                    // LazyVerticalGrid 3 column of transaction, belong to this invest: modifier = Modifier.padding(2.dp).weight(1f)
                    // simple delete button, icon of delete

                }
                Button(onClick = {stockViewModel.deleteInvest(invest)}) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
    if(showDetail){
        stockViewModel.currentInvest = invest
        DetailOfInvest(stockViewModel = stockViewModel, navController = navController, invest = invest, {showDetail = false})
    }
}

// add new invest
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateInvest(stockViewModel: StockViewModel, navController: NavController, onDismiss: () -> Unit) {
    var showDialog by remember { mutableStateOf(true) }
    var currentText by remember { mutableStateOf("") }
    if (showDialog) {
        // dialog contain card, color different from default, beatiful, height = 120dp(belong to card)
        Dialog(onDismissRequest = {
            showDialog = false
            onDismiss()
        }) {
            Card(
                modifier = Modifier
                    .padding(3.dp)
                    .height(120.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondaryContainer)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    // outlinetextfield, placeholder, label
                    // button of add
                    // button of cancel
                    // make this outlinetext filed has color white of text inside it both typing and lable and placholder, lacble = Text("Name of invest"), place holder =Text("Name of invest"), as short as possible
                    OutlinedTextField(
                        value = currentText,
                        onValueChange = {
                            currentText = it
                        },
                        label = {
                            Text("Name of invest")
                        },
                        placeholder = {
                            Text("Name of invest")
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White,
                            placeholderColor = Color.White,
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            disabledBorderColor = Color.White
                        )
                    )

                    Row(
                        modifier = Modifier.padding(3.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {
                            showDialog = false
                            onDismiss()
                        }) {
                            Text("Cancel")
                        }
                        Button(onClick = {
                            // add new invest
                            stockViewModel.newInvest(currentText)
                            showDialog = false
                            onDismiss()
                        }) {
                            Text("Add")
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun SecondScreen(stockViewModel: StockViewModel, navController: NavController) {
    stockViewModel.getCompanytoDisplay("")
    var createInvest by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn(Modifier.height(ComposableSize.viewStockHeight.dp)) {
            items(stockViewModel.investList) { invest ->
                SimpleCardOfInvest(
                    stockViewModel = stockViewModel,
                    navController = navController,
                    invest = invest,
                    onClick = {
                        stockViewModel.resetAllTransactionPrice()
                    }
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text("Total interest: " + stockViewModel.totalInterestTransactions.value.toString(), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally))

        Row(modifier = Modifier.padding(3.dp)) {
            Button(onClick = {
                stockViewModel.resetAllTransactionPrice()
            }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
            }
            Button(onClick = {
                createInvest = true
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add invest")
            }
        }
        BottomBar(stockViewModel = stockViewModel, navController = navController)
    }
    if(createInvest){
        CreateInvest(stockViewModel = stockViewModel, navController = navController, {createInvest = false})
    }
}