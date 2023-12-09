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

    @Query("SELECT * FROM CompanyName WHERE symbol LIKE '%' || :text || '%' OR name LIKE '%' || :text || '%'")
    fun getCompanyCharacter(text: String): Flow<List<CompanyName>>
    @Query("select Count(*) from CompanyName")
    suspend fun getSize(): Int

    @Query("delete from CompanyName")
    suspend fun deleteTable()

    @Query("select * from CompanyName where id = :id")
    fun findCompanyById(id: Int): CompanyName?

    @Update
    suspend fun updatePrice(companyName: CompanyName)

    @Query("select * from CompanyName where id=:id")
    suspend fun getCompanyById(id: Int): CompanyName
}