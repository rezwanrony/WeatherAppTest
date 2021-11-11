package com.rezwan.weatherapptest.module

import android.content.Context
import com.rezwan.weatherapptest.interfaces.IContext
import com.rezwan.weatherapptest.scope.WeatherApplicationScope
import dagger.Module
import dagger.Provides

@Module
class ContextModule(private val context: Context) {
    @IContext
    @WeatherApplicationScope
    @Provides
    fun context(): Context {
        return context.applicationContext
    }
}