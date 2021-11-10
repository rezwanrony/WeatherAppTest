package com.rezwan.weatherapptest.components

import com.rezwan.weatherapptest.interfaces.WeatherApi
import com.rezwan.weatherapptest.module.WeatherApiModule
import com.rezwan.weatherapptest.scope.WeatherApplicationScope
import dagger.Component

@Component(modules = [WeatherApiModule::class])
@WeatherApplicationScope
interface WeatherApiComponent {
    fun getWeatherApi(): WeatherApi?
}