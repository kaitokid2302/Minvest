package com.example.minvest.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.minvest.MVVM.Data.CompanyName
import com.example.minvest.MVVM.Data.RealObject.Link
import com.example.minvest.MVVM.Data.Transaction
import com.example.minvest.MVVM.SortBy
import com.example.minvest.MVVM.StockViewModel

@Composable
fun BottomBar(stockViewModel: StockViewModel, navController: NavController){
    Row(modifier = Modifier.fillMaxWidth()){
        Button(onClick = { navController.navigate("First Screen") },
            modifier = Modifier.weight(1f)) {
            Text("Stocks", modifier = Modifier.align(Alignment.CenterVertically))
        }
        Button(onClick = { navController.navigate("Second Screen") },
            modifier = Modifier.weight(1f)) {
            Text("My invests", modifier = Modifier.align(Alignment.CenterVertically))
        }
        Button(onClick = { navController.navigate("Third Screen") },
            modifier = Modifier.weight(1f)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Text("My")
                Text(" Transactions")

            }
        }
    }
}
