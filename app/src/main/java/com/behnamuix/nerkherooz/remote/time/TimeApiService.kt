package com.behnamuix.nerkherooz.remote.time

import com.behnamuix.nerkherooz.remote.timeModel.DateModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TimeApiService {
    @GET("date/now")

    fun getTime(
        //paramter sending
        @Query("short") short:Boolean

    ):Call<DateModel>
}