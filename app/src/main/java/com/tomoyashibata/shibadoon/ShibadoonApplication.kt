package com.tomoyashibata.shibadoon

import android.app.Application
import com.tomoyashibata.shibadoon.di.networkModule
import com.tomoyashibata.shibadoon.di.repositoryModule
import com.tomoyashibata.shibadoon.di.shibadoonModule
import com.tomoyashibata.shibadoon.di.useCaseModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber.DebugTree
import timber.log.Timber


class ShibadoonApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    startKoin(this, listOf(
      shibadoonModule,
      networkModule,
      repositoryModule,
      useCaseModule
    ))

    if (BuildConfig.DEBUG) {
      Timber.plant(DebugTree())
    }
  }
}
