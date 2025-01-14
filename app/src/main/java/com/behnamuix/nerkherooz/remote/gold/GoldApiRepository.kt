    package com.behnamuix.nerkherooz.remote.gold

import com.behnamuix.nerkherooz.remote.RetrofitService
import com.behnamuix.nerkherooz.remote.goldModel.GoldModel
import com.behnamuix.nerkherooz.remote.timeModel.DateModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoldApiRepository private constructor(){
    companion object{
        private var apiRepository: GoldApiRepository?=null
        val instance: GoldApiRepository
        //singleton
        get(){
            if(apiRepository ==null) apiRepository = GoldApiRepository()
            return apiRepository!!
        }
    }
    fun getGold(
        request: GoldReq
    ){
        RetrofitService.goldApiservice.getGold().enqueue(
            object :Callback<GoldModel>{
                override fun onResponse(call: Call<GoldModel>, response: Response<GoldModel>) {
                    if(response.isSuccessful){
                        val date=response.body() as GoldModel
                        request.OnSuccess(date)
                    }else
                        request.OnNotSuccess("Not Success")
                }

                override fun onFailure(call: Call<GoldModel>, t: Throwable) {
                    request.OnError("Error :${t.message}")
                }

            }

        )
    }
}