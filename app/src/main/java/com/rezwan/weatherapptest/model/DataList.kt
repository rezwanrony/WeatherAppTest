package com.rezwan.weatherapptest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataList<T> {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("coord")
    @Expose
    var coord: CoordinatesData? = null

    @SerializedName("main")
    @Expose
    var main: CurrentWeatherDetails? = null

    @SerializedName("dt")
    @Expose
    var dt: Int? = null

    @SerializedName("wind")
    @Expose
    var wind: Wind? = null

    @SerializedName("sys")
    @Expose
    var sys: Country? = null

    @SerializedName("rain")
    @Expose
    var rain: Any? = null

    @SerializedName("snow")
    @Expose
    var snow: Any? = null

    @SerializedName("clouds")
    @Expose
    var clouds: CloudResponse? = null

    @SerializedName("weather")
    @Expose
    var weather: java.util.List<Weather>? = null
}