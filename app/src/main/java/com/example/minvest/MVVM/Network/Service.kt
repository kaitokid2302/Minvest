package com.example.minvest.MVVM.Network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Service {
    private const val BASE_URL = "https://twelve-data1.p.rapidapi.com/"

    fun companyName(builder: Retrofit.Builder): CompanyAPI{
        return builder.build().create(CompanyAPI::class.java)
    }
    fun provideRetrofit(): Retrofit.Builder {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }
}