package com.example.minvest.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavController
import com.example.minvest.MVVM.Data.CompanyName
import com.example.minvest.MVVM.StockViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchField(modifier: Modifier, stockViewModel: StockViewModel, navController: NavController){
    var text by remember{
        mutableStateOf("")
    }
    var keyBoardController = LocalSoftwareKeyboardController.current
    Row(modifier = modifier){
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    stockViewModel.onSearch{ keyBoardController?.hide() }
                }
            )
        )
        IconButton(onClick = {
            stockViewModel.onSearch{ keyBoardController?.hide() }
        }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "search")
        }
    }
}

@Composable
fun SimpleCompanyCard(modifier: Modifier, companyName: CompanyName){
    Card(modifier = modifier){
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Text("${companyName.name}", style = MaterialTheme.typography.titleMedium)
            Text("${companyName.symbol}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfCompany(modifier: Modifier, stockViewModel: StockViewModel, navController: NavController, symbol: String = "all"){
    var cur = stockViewModel.currentCompanyName
    if(symbol != "all" && symbol != "no"){
        stockViewModel.getCompany(symbol)
    }
    if(symbol != "no"){

    }
}