package com.tomoyashibata.shibadoon.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tomoyashibata.shibadoon.model.network.MastodonApi
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
  single { (instance: String) ->
    val moshi = Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .build()

    Retrofit.Builder()
      .baseUrl("https://${instance}/")
      .addCallAdapterFactory(CoroutineCallAdapterFactory())
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()
      .create(MastodonApi::class.java)
  }
}
