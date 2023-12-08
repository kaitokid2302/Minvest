package com.example.minvest.Variable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class LoginCredentia : ViewModel() {

    var username by mutableStateOf("")
    var password by mutableStateOf("")
}