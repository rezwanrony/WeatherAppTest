package com.rezwan.weatherapptest.application

import android.app.Activity
import android.app.Application
import com.rezwan.weatherapptest.components.WeatherApiComponent
import com.rezwan.weatherapptest.module.ContextModule

class WeatherApplication : Application() {
    private var weatherApiComponentApplication: WeatherApiComponent? = null
    override fun onCreate() {
        super.onCreate()
        weatherApiComponentApplication = DaggerWeatherApiComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }

    fun getWeatherApiComponentApplication(): WeatherApiComponent? {
        return weatherApiComponentApplication
    }

    companion object {
        operator fun get(activity: Activity): WeatherApplication {
            return activity.getApplication() as WeatherApplication
        }
    }
}