package me.alfredobejarano.brastlewarkarchitecturecomponents.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import me.alfredobejarano.brastlewarkarchitecturecomponents.BuildConfig
import me.alfredobejarano.brastlewarkarchitecturecomponents.datasource.local.BrastlewarkDatabase
import me.alfredobejarano.brastlewarkarchitecturecomponents.datasource.local.GnomeCacheTimeStampDataSource
import me.alfredobejarano.brastlewarkarchitecturecomponents.datasource.remote.GnomeApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataSourceModule(private val app: Application) {
    private val gson: Gson by lazy { GsonBuilder().excludeFieldsWithoutExposeAnnotation().create() }

    private val gsonConverter: GsonConverterFactory by lazy { GsonConverterFactory.create(gson) }

    private val httpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(gsonConverter)
            .baseUrl(BuildConfig.SERVER_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideGnomeApiService(): GnomeApiService = retrofit.create(GnomeApiService::class.java)

    @Provides
    @Singleton
    fun provideGnomeDao() = BrastlewarkDatabase.getInstance(app).provideGnomeDao()

    @Provides
    @Singleton
    fun provideGnomeCacheTimeStampDataSource() = GnomeCacheTimeStampDataSource(app)
}