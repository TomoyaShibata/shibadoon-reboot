package com.tomoyashibata.shibadoon.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tomoyashibata.shibadoon.model.network.MastodonApi
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
  single {
    Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .build()
  }
  factory { (instance: String) ->
    Retrofit.Builder()
      .baseUrl("https://$instance/")
      .addCallAdapterFactory(CoroutineCallAdapterFactory())
      .addConverterFactory(MoshiConverterFactory.create(get()))
      .build()
      .create(MastodonApi::class.java)
  }
}
