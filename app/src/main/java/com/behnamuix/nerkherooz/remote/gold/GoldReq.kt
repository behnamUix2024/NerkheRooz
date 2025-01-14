package com.behnamuix.nerkherooz.remote.gold

import com.behnamuix.nerkherooz.remote.goldModel.GoldModel

interface GoldReq {
    fun OnSuccess(date:GoldModel)
    fun OnNotSuccess(msg:String)
    fun OnError(err:String)
}