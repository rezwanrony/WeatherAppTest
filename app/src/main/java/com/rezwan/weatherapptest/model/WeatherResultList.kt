package com.rezwan.weatherapptest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherResultList {
    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("cod")
    @Expose
    var cod: String? = null

    @SerializedName("count")
    @Expose
    var count: Int? = null

    @SerializedName("list")
    @Expose
    var dataList: kotlin.collections.List<DataList<Any?>>? = null
}