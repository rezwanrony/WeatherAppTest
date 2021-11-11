package com.rezwan.weatherapptest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CurrentWeather {
    @SerializedName("coord")
    @Expose
    var coord: CoordinatesData? = null

    @SerializedName("weather")
    @Expose
    var weather: DataList<Weather>? = null

    @SerializedName("base")
    @Expose
    var base: String? = null

    @SerializedName("main")
    @Expose
    var main: CurrentWeatherDetails? = null

    @SerializedName("visibility")
    @Expose
    var visibility: Int? = null

    @SerializedName("wind")
    @Expose
    var wind: Wind? = null

    @SerializedName("clouds")
    @Expose
    var clouds: CloudResponse? = null

    @SerializedName("dt")
    @Expose
    var dt: Int? = null

    @SerializedName("sys")
    @Expose
    var sys: Country? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("cod")
    @Expose
    var cod: Int? = null
}