package com.tomoyashibata.shibadoon.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tomoyashibata.shibadoon.database.AppDatabase
import com.tomoyashibata.shibadoon.model.network.MastodonApi
import com.tomoyashibata.shibadoon.model.repository.*
import com.tomoyashibata.shibadoon.model.usecase.*
import com.tomoyashibata.shibadoon.ui.createtoot.CreateTootViewModel
import com.tomoyashibata.shibadoon.ui.home.HomeViewModel
import com.tomoyashibata.shibadoon.ui.hometimeline.HomeTimelineViewModel
import com.tomoyashibata.shibadoon.ui.login.LoginViewModel
import com.tomoyashibata.shibadoon.ui.notifications.NotificationsViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

val shibadoonModule = module {
  viewModel { CreateTootViewModel(get()) }
  viewModel { HomeViewModel(get(), get(), get(), get()) }
  viewModel { HomeTimelineViewModel(get(), get(), get(), get()) }
  viewModel { LoginViewModel(get(), get()) }
  viewModel { NotificationsViewModel(get()) }
}

val useCaseModule = module {
  single { ChangeCurrentSavedAccountUseCase(get()) }
  single { CreateTootUseCase(get()) }
  single { FetchHomeTimelineUseCase(get()) }
  single { FetchNotificationsUseCase(get()) }
  single { FetchOldHomeTimelineUseCase(get()) }
  single { FetchStatusUseCase(get()) }
  single { GetAccountsUseCase(get()) }
  single { GetCurrentSavedAccountUseCase(get(), get()) }
  single { GetSavedAccountsUseCase(get(), get()) }
  single { GetStreamingUserRequestUseCase(get()) }
  single { GetTokenUseCase(get(), get()) }
  single { HasSavedTokenUseCase(get()) }
  single { LoginUseCase(get()) }
  single { ToggleFavouriteUseCase(get()) }
  single { ToggleReblogUseCase(get()) }
}


val repositoryModule = module {
  single { AccountRepository() }
  single { AppRepository() }
  single { NotificationsRepository() }
  single { SavedAccessTokenRepository(get(), get()) }
  single { StatusesRepository() }
  single { AccessTokenRepository(get()) }
  single { TimelinesRepository() }
}

val databaseModule = module {
  single {
    Room.databaseBuilder(this.androidContext(), AppDatabase::class.java, "shibadoon")
      .fallbackToDestructiveMigration()
      .build()
  }
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

  factory<MastodonApi>("withParams") { (instance: String, accessToken: String) ->
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

  factory<MastodonApi>("currentAccount") {
    val currentSavedAccessToken = get<SavedAccessTokenRepository>().getCurrentSavedAccessToken()

    Retrofit.Builder()
      .baseUrl("https://${currentSavedAccessToken.instance}/")
      .addCallAdapterFactory(CoroutineCallAdapterFactory())
      .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()))
      .also {
        it.client(OkHttpClient.Builder().apply {
          addInterceptor(AuthenticationInterceptor(currentSavedAccessToken.accessToken))
        }.build())
      }.build().create(MastodonApi::class.java)
  }

  factory("streamingUser") {
    val currentSavedAccessToken = get<SavedAccessTokenRepository>().getCurrentSavedAccessToken()
    Request.Builder()
      .header("Authorization", "Bearer ${currentSavedAccessToken.accessToken}")
      .url("https://${currentSavedAccessToken.instance}/api/v1/streaming/user")
      .build()
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

