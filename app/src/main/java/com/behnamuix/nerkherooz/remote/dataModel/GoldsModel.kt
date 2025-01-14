package com.behnamuix.nerkherooz.remote.goldModel

data class GoldModel(
    val message: String,
    val data: AllData
)

data class AllData(
    val golds: List<ContentModel>,
    val currencies: List<ContentModel>,
    val cryptocurrencies: List<ContentModel>
)

data class ContentModel(
    val label: String,
    val price: Int,
    val symbol:String
)
