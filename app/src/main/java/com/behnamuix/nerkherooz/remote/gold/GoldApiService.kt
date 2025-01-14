package com.behnamuix.nerkherooz.remote.gold

import com.behnamuix.nerkherooz.remote.goldModel.GoldModel
import retrofit2.Call
import retrofit2.http.GET

interface GoldApiService {
    @GET("currencies")

    fun getGold():Call<GoldModel>
}