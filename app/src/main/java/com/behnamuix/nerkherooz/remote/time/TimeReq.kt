package com.behnamuix.nerkherooz.remote.time

import com.behnamuix.nerkherooz.remote.timeModel.DateModel

interface TimeReq {
    fun OnSuccess(date: DateModel)
    fun OnNotSuccess(msg:String)
    fun OnError(err:String)
}