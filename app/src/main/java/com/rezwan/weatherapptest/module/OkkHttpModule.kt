package com.rezwan.weatherapptest.module

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import com.rezwan.weatherapptest.interfaces.IContext
import com.rezwan.weatherapptest.scope.WeatherApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File

@Module(includes = [ContextModule::class])
class OkkHttpModule {
    @Provides
    fun okHttpClient(cache: Cache?, httpLoggingInterceptor: HttpLoggingInterceptor?): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .cache(cache)
            .addInterceptor(httpLoggingInterceptor!!)
            .build()
    }

    @Provides
    fun cache(cacheFile: File?): Cache {
        return Cache(cacheFile!!, 10 * 1000 * 1000) //10 MB
    }

    @Provides
    @WeatherApplicationScope
    fun file(@IContext context: Context): File {
        return File(context.cacheDir, "HttpCache")
    }

    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(@NonNull message: String) {
                Log.d("NETWORK", message)
            }
        })
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return httpLoggingInterceptor
    }
}