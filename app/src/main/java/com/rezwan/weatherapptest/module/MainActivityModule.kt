package com.rezwan.weatherapptest.module

import com.rezwan.weatherapptest.ui.HomeActivity
import com.rezwan.weatherapptest.adapter.WeatherResultListAdepter
import com.rezwan.weatherapptest.scope.MainActivityScope
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(activity: HomeActivity) {
    private val activity: HomeActivity
    @MainActivityScope
    @Provides
    fun weatherResultListAdepter(): WeatherResultListAdepter {
        return WeatherResultListAdepter(activity)
    }

    init {
        this.activity = activity
    }
}