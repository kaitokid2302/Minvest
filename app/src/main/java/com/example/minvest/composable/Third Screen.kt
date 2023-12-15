package com.example.minvest.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.minvest.MVVM.Data.RealObject.Link
import com.example.minvest.MVVM.SortBy
import com.example.minvest.MVVM.StockViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

@Composable
fun CardTransaction(stockViewModel: StockViewModel, link: Link, modifier: Modifier = Modifier
    .padding(5.dp)
    .fillMaxWidth()
    .height(110.dp)){
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer)){
        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.weight(1f)){
                /*
                Company
                date
                buying price
                selling price
                quanity
                interest
                 */
                Text("${link.companyName.name}", style = MaterialTheme.typography.titleMedium)
                Text("${stockViewModel.formatTimestamp(link.transaction.time)}", style = MaterialTheme.typography.bodySmall, color = Color.White)
                Text("Buying price: " + link.transaction.previousPrice.toString(), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = Color.White)
                Text("Price now: " + link.transaction.currentPrice.toString(), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = Color.White)
                Text(text = "Spend: " + stockViewModel.fourDecimalDigit(link.transaction.previousPrice * link.transaction.quanity).toString(), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = Color.White)
                Text("Quantity: " + link.transaction.quanity.toString(), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = Color.White)
                var interest = stockViewModel.calculateInterest(link)
                Row{
                    Text("Interest: " + stockViewModel.fourDecimalDigit(interest).toString(), color = Color.White)
                    var tint = Color.Green
                    if(interest < 0.0) tint = Color.Red
                    Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "up", tint = tint)
                }
            }
            IconButton(onClick = { stockViewModel.deleteTransaction(link.transaction) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "delete", tint = Color.White)
            }
        }
    }
}

@Composable
fun RadioButtonForPrice(stockViewModel: StockViewModel, selected: SortBy?, cur: SortBy, modifier: Modifier, changeSelected: () -> Unit){
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
        RadioButton(selected = cur==selected , onClick = {
            changeSelected()
        })
        when(cur){
            SortBy.byInterestAsc -> {
                Row{
                    Text("Interest")
                    Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "up", tint = Color.Red)
                }

            }
            SortBy.byInterestDes -> {
                Row{
                    Text("Interest")
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "down", tint = Color.Green)
                }
            }
            else -> {

            }
        }

    }
}

@Composable
fun ThirdScreen(stockViewModel: StockViewModel, navController: NavController){
    var transactionList = stockViewModel.transactionList
    var selected by remember{
        mutableStateOf<SortBy?> (null)
    }
    Column(modifier = Modifier.padding(5.dp)){
            Column(modifier = Modifier.height(ComposableSize.viewStockHeight.dp)) {
                LazyColumn(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxHeight()
                ) {

                    items(transactionList) {
                        CardTransaction(stockViewModel = stockViewModel, link = it)
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {stockViewModel.resetAllTransactionPrice()}) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "refresh")
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
            Text("Total interest: " + stockViewModel.fourDecimalDigit(stockViewModel.totalInterestTransactions.value).toString(), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            if(stockViewModel.totalInterestTransactions.value < 0.0) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "down",
                    tint = Color.Red,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            else{
                Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "up", tint = Color.Green, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }
            Row(modifier = Modifier
                .padding(3.dp)
                .height(ComposableSize.viewRadioButonHeight.dp)){
                SortBy::class.sealedSubclasses.forEach {
                    var cur = it.objectInstance!!
                    if(cur is SortBy.byInterestDes || cur is SortBy.byInterestAsc){
                        RadioButtonForPrice(stockViewModel = stockViewModel, selected = selected, cur = cur, modifier = Modifier.weight(1f)) {
                            selected = cur
                            stockViewModel.sortBy(cur)
                            stockViewModel.sortBy(cur)
                        }
                    }
                }
            }
            BottomBar(stockViewModel = stockViewModel, navController = navController)
        }
}
// finish all