package com.tomoyashibata.shibadoon.di

import android.arch.persistence.room.Room
import com.tomoyashibata.shibadoon.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val databaseModule = module {
  single {
    Room.databaseBuilder(this.androidContext(), AppDatabase::class.java, "shibadoon")
      .fallbackToDestructiveMigration()
      .build()
  }
}
