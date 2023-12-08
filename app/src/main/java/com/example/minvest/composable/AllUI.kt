package com.example.minvest.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavController
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
                onDone ={
                    keyBoardController?.hide()
                }
            )
        )
    }
}