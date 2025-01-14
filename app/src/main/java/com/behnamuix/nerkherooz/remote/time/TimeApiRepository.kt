    package com.behnamuix.nerkherooz.remote.time

import com.behnamuix.nerkherooz.remote.RetrofitService
import com.behnamuix.nerkherooz.remote.timeModel.DateModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TimeApiRepository private constructor(){
    companion object{
        private var apiRepository: TimeApiRepository?=null
        val instance: TimeApiRepository
        //singleton
        get(){
            if(apiRepository ==null) apiRepository = TimeApiRepository()
            return apiRepository!!
        }
    }
    fun getTime(
        request: TimeReq
    ){
        RetrofitService.apiservice.getTime(true).enqueue(
            object :Callback<DateModel>{
                override fun onResponse(call: Call<DateModel>, response: Response<DateModel>) {
                    if(response.isSuccessful){
                        val date=response.body() as DateModel
                        request.OnSuccess(date)
                    }else
                        request.OnNotSuccess("Not Success")
                }

                override fun onFailure(call: Call<DateModel>, t: Throwable) {
                    request.OnError("Error :${t.message}")
                }

            }

        )
    }
}