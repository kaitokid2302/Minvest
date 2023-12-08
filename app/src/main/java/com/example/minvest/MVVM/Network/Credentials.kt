package com.example.minvest.MVVM.Network

object Credentials {

    var tokenArray = arrayOf("5d86ff9536msh47eda5f94d5216fp14d56ajsn689f", "ee3ac6131cmsh013cfd480193c39p16413cjsn472d9447a3fd", "4e2db0a3c4msh66a2f0b81e4560cp1b1cf4jsne135")
    var token = "5d86ff9536msh47eda5f94d5216fp14d56ajsn689f"
    var host = "twelve-data1.p.rapidapi.com"
    var cur = 0;
    fun changeToken(){
        cur++;
        cur %= tokenArray.size
        token = tokenArray[cur]
    }
}