package com.example.minvest.MVVM.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface InvestDAO {
    @Insert
    suspend fun createNewInvest(invest: Invest)

    @Delete
    suspend fun deleteInvest(invest: Invest)

    @Query("select * from Invest order by id")
    fun allInvest(): Flow<List<Invest>>

    @Query("select * from Invest where id = :id limit 1")
    suspend fun findInvest(id: Int): Invest
    @Update
    suspend fun updateInvest(invest: Invest)
}