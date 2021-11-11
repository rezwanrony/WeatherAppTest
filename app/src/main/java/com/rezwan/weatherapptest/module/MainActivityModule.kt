package com.rezwan.weatherapptest.module

import com.rezwan.weatherapptest.ui.HomeActivity
import com.rezwan.weatherapptest.adapter.WeatherListAdapter
import com.rezwan.weatherapptest.scope.MainActivityScope
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(activity: HomeActivity) {
    private val activity: HomeActivity
    @MainActivityScope
    @Provides
    fun weatherResultListAdepter(): WeatherListAdapter {
        return WeatherListAdapter(activity)
    }

    init {
        this.activity = activity
    }
}