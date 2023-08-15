package com.apex.codeassesment.di

import android.content.Context
import android.content.SharedPreferences
import com.apex.codeassesment.api.ApiService
import com.apex.codeassesment.data.local.LocalDataSource
import com.apex.codeassesment.data.local.PreferencesManager
import com.apex.codeassesment.data.remote.RemoteDataSource
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object MainModule {

  @Provides
  fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("random-user-preferences", Context.MODE_PRIVATE)
  }

  @Provides
  fun providePreferencesManager(): PreferencesManager = PreferencesManager()

  @Provides
  fun provideMoshi(): Moshi {
    return Moshi.Builder().build()
  }

  @Provides
  fun provideLocalDataSource(): LocalDataSource {
    return LocalDataSource(providePreferencesManager(), provideMoshi())
  }

  @Provides
  @Singleton
  fun provideRetrofit(): Retrofit {

    val loggingInterceptor = HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY // Change the level as needed
    }

    val client = OkHttpClient.Builder()
      .addInterceptor(loggingInterceptor)
      .build()
    return Retrofit.Builder()
      .client(client)
      .baseUrl("https://randomuser.me/") // Set your base URL here
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }
  @Provides
  @Singleton
  fun provideApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
  }

  @Provides
  fun provideRemoteDataSource(): RemoteDataSource {
    return RemoteDataSource(provideApiService(provideRetrofit()))
  }
}