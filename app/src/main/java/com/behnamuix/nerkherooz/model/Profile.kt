package com.behnamuix.nerkherooz.model

import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("date") val date: String
)
