package com.example.minvest.MVVM.Network

object Credentials {

    var tokenArray = arrayOf("5d86ff9536msh47eda5f94d5216fp14d56ajsn689f6f7a694f",
        "ee3ac6131cmsh013cfd480193c39p16413cjsn472d9447a3fd",
        "4e2db0a3c4msh66a2f0b81e4560cp1b1cf4jsne1352ea7bb7c",
        "16f68568b3msh2400c7783e11af9p158910jsnc1130bf39e0b",
        "d2984fe2ecmshb916fc794a548d1p174e21jsn8bb528e34855",
        "66505dbcfemshf8963e3e66c5713p13c406jsn47bad3f5b6ed",
        "c08d5ccd35msh4783c0239f0f38ap1b9f3ejsnc4e3ed59b873")
    var token = tokenArray[6]
    var host = "twelve-data1.p.rapidapi.com"
    var cur = 6;
    fun changeToken(){
        cur++;
        cur %= tokenArray.size
        token = tokenArray[cur]
    }
}