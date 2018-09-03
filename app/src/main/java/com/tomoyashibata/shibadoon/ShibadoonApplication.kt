package com.tomoyashibata.shibadoon

import android.app.Application
import co.zsmb.materialdrawerkt.imageloader.drawerImageLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.jakewharton.threetenabp.AndroidThreeTen
import com.tomoyashibata.shibadoon.di.*
import org.koin.android.ext.android.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class ShibadoonApplication : Application() {
  override fun onCreate() {
    super.onCreate()

    this.setupTimber()

    AndroidThreeTen.init(this)

    this.setupKoin()
    this.setupMaterialDrawer()
  }

  private fun setupTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(DebugTree())
    }
  }

  private fun setupKoin() {
    startKoin(this, listOf(
      shibadoonModule,
      sharedPreferenceModule,
      databaseModule,
      networkModule,
      repositoryModule,
      useCaseModule
    ))
  }

  private fun setupMaterialDrawer() {
    drawerImageLoader {
      set { imageView, uri, _, _ -> Glide.with(imageView.context).load(uri).into(imageView) }
      cancel { Glide.with(it.context).clear(it) }
    }
  }
}

@GlideModule
class MyAppGlideModule() : AppGlideModule()
