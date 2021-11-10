package com.rezwan.weatherapptest.module

import android.content.Context
import com.rezwan.weatherapptest.interfaces.ApplicationContext
import com.rezwan.weatherapptest.scope.WeatherApplicationScope
import dagger.Module
import dagger.Provides

@Module
class ContextModule(private val context: Context) {
    @ApplicationContext
    @WeatherApplicationScope
    @Provides
    fun context(): Context {
        return context.applicationContext
    }
}