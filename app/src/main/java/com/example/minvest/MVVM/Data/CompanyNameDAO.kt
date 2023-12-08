package com.example.minvest.MVVM.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface CompanyNameDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertName(companyName: CompanyName)
    @Query("select * from CompanyName")
    fun getAllName(): Flow<List<CompanyName>>

    @Query("select * from CompanyName where symbol=:symbol limit 1")
    suspend fun getCompany(symbol: String): CompanyName?

    @Query("select Count(*) from CompanyName")
    suspend fun getSize(): Int

    @Query("delete from CompanyName")
    suspend fun deleteTable()

    @Update
    suspend fun updatePrice(companyName: CompanyName)
}