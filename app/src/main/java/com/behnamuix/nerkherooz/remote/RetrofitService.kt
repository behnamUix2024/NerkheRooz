package com.behnamuix.nerkherooz.remote

import com.behnamuix.nerkherooz.remote.gold.GoldApiService
import com.behnamuix.nerkherooz.remote.time.TimeApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val url="https://tools.daneshjooyar.com/api/v1/"
    private val retrofit=Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiservice: TimeApiService = retrofit.create(TimeApiService::class.java)
    val goldApiservice: GoldApiService = retrofit.create(GoldApiService::class.java)

}