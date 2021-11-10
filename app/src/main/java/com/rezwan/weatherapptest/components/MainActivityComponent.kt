package com.rezwan.weatherapptest.components

import com.rezwan.weatherapptest.ui.HomeActivity
import com.rezwan.weatherapptest.module.MainActivityModule
import com.rezwan.weatherapptest.scope.MainActivityScope
import dagger.Component

@Component(modules = [MainActivityModule::class], dependencies = [WeatherApiComponent::class])
@MainActivityScope
interface MainActivityComponent {
    fun injectMainActivity(activity: HomeActivity?)
}