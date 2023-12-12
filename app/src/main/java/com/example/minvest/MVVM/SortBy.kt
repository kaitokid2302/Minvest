package com.example.minvest.MVVM

sealed class SortBy{
    object byPriceASC: SortBy()
    object byPriceDES: SortBy()
    object bySymbolASC: SortBy()
    object bySymbolDES: SortBy()
    object byInterestAsc: SortBy()

    object byInterestDes: SortBy()
}