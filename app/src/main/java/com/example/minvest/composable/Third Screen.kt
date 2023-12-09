package com.example.minvest.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.minvest.MVVM.StockViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

@Composable
fun RadioButtonForPrice(stockViewModel: StockViewModel, navController: NavController){

}

@Composable
fun ThirdScreen(stockViewModel: StockViewModel, navController: NavController){
    var transactionList = stockViewModel.transactionList
    var refresh by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(refresh) {
        if (refresh) {
            delay(200)
            refresh = false
        }
    }
    Column {
        SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = refresh), onRefresh = {
            refresh = true
        }) {
            LazyColumn(modifier = Modifier.padding(5.dp)){
                items(transactionList){
                    CardTransaction(stockViewModel = stockViewModel, link = it)
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            BottomBar(stockViewModel = stockViewModel, navController = navController)
        }
    }

}