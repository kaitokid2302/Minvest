package com.example.minvest.MVVM.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CompanyName::class], version = 1)
abstract class CompanyNameDB: RoomDatabase(){
    abstract fun getCompanyNameDAO() : CompanyNameDAO
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
                        "Company_Name_DB"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}