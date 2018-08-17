package com.tomoyashibata.shibadoon.di

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tomoyashibata.shibadoon.model.network.MastodonApi
import com.tomoyashibata.shibadoon.ui.home.HomeViewModel
import com.tomoyashibata.shibadoon.ui.login.LoginViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

val shibadoonModule = module {
  viewModel { HomeViewModel(get(), get(), get(), get()) }
  viewModel { LoginViewModel(get(), get()) }
}

val sharedPreferenceModule = module {
  single {
    androidContext().getSharedPreferences("shibadoon", Context.MODE_PRIVATE)
  }
}

val networkModule = module {
  single {
    Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .build()
  }

  factory { (instance: String, accessToken: String) ->
    val builder = Retrofit.Builder()
      .baseUrl("https://$instance/")
      .addCallAdapterFactory(CoroutineCallAdapterFactory())
      .addConverterFactory(MoshiConverterFactory.create(get()))

    if (accessToken.isNotBlank()) {
      builder.client(OkHttpClient.Builder().apply {
        addInterceptor(AuthenticationInterceptor(accessToken))
      }.build())
    }

    builder.build().create(MastodonApi::class.java)
  }
}

class AuthenticationInterceptor(private val authToken: String) : Interceptor {
  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    val original = chain.request()

    val builder = original.newBuilder()
      .header("Authorization", "Bearer $authToken")

    val request = builder.build()
    return chain.proceed(request)
  }
}

