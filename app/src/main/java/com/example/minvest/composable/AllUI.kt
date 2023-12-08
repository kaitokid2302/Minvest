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
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(modifier: Modifier){
    var text by remember{
        mutableStateOf("")
    }
    Row(modifier = modifier){
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            keyboardActions = KeyboardActions(
                onDone = {

                }
            )
        )
    }
}