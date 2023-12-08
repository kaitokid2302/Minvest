package com.example.minvest.MVVM.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDAO {
    @Delete
    suspend fun deleteTransaction(cur: Transaction)

    @Query("select * from `Transaction` order by id")
    fun allTransaction(): Flow<List<Transaction>>

    @Query("select * from `Transaction` where invest_id=:invest_id")
    fun getTransactionOfInvestment(invest_id: Int): Flow<List<Transaction>>
    @Insert
    suspend fun insertTransaction(cur: Transaction)

    @Query("delete from `Transaction` where invest_id = :invest_id")
    suspend fun deleteInvest(invest_id: Int)
}