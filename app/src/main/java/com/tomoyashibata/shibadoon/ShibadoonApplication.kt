package com.tomoyashibata.shibadoon

import android.app.Application
import com.tomoyashibata.shibadoon.di.*
import org.koin.android.ext.android.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class ShibadoonApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    startKoin(this, listOf(
      shibadoonModule,
      databaseModule,
      networkModule,
      repositoryModule,
      useCaseModule
    ))

    if (BuildConfig.DEBUG) {
      Timber.plant(DebugTree())
    }
  }
}
