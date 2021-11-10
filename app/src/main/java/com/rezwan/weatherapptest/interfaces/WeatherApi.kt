package com.rezwan.weatherapptest.interfaces

import com.rezwan.weatherapptest.model.CurrentWeather
import com.rezwan.weatherapptest.model.WeatherResultList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/data/2.5/find?")
    fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("cnt") cnt: Int,
        @Query("appid") appid: String?,
        @Query("units") units: String?
    ): Call<WeatherResultList?>?

    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("cnt") cnt: Int,
        @Query("appid") appid: String?,
        @Query("units") units: String?
    ): Call<CurrentWeather?>?
}