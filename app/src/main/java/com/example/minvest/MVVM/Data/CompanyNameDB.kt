package com.example.minvest.MVVM.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CompanyName::class, Invest::class, Transaction::class], version = 2)
abstract class CompanyNameDB: RoomDatabase(){
    abstract fun getCompanyNameDAO() : CompanyNameDAO
    abstract fun getInvest(): InvestDAO
    abstract fun getTransaction(): TransactionDAO
    companion object {
        @Volatile
        private var INSTANCE: CompanyNameDB? = null
        fun getInstance(context: Context): CompanyNameDB {
            synchronized(this) {
                var instance = INSTANCE;
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CompanyNameDB::class.java,
                        "Stock"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}