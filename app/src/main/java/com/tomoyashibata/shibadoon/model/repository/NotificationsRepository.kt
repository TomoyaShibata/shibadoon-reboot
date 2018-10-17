package com.tomoyashibata.shibadoon.model.repository

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tomoyashibata.shibadoon.di.AuthenticationInterceptor
import com.tomoyashibata.shibadoon.model.data.Notification
import com.tomoyashibata.shibadoon.model.network.MastodonApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NotificationsRepository {
  private val service = Retrofit.Builder()
    .baseUrl("https://m6n.onsen.tech/")
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .build()))
    .also {
      it.client(OkHttpClient.Builder().apply {
        addInterceptor(AuthenticationInterceptor("0122011c0c11d0c2905d3050042e0a0a8f39940e40b5f3d91a755bc78f311979"))
      }.build())
    }.build().create(MastodonApi::class.java)

  fun fetchNotifications(): List<Notification> {
    val result = this.service.fetchNotification().execute()
    return result.body() ?: throw Exception(result.errorBody()?.string())
  }

  fun fetchNotifications(maxId: Long): List<Notification> {
    val result = this.service.fetchNotification(maxId = maxId).execute()
    return result.body() ?: throw Exception(result.errorBody()?.string())
  }
}
