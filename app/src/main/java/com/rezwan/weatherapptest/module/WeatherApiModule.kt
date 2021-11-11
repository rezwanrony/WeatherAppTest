package com.rezwan.weatherapptest.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rezwan.weatherapptest.interfaces.IWeather
import com.rezwan.weatherapptest.scope.WeatherApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [OkkHttpModule::class])
class WeatherApiModule {
    @Provides
    fun randomUsersApi(retrofit: Retrofit): IWeather {
        return retrofit.create(IWeather::class.java)
    }

    @WeatherApplicationScope
    @Provides
    fun retrofit(
        okHttpClient: OkHttpClient?,
        gsonConverterFactory: GsonConverterFactory?
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://api.openweathermap.org/")
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    fun gsonConverterFactory(gson: Gson?): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    fun gson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }
}