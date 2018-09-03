package com.tomoyashibata.shibadoon.di

import com.tomoyashibata.shibadoon.model.repository.*
import org.koin.dsl.module.module

val repositoryModule = module {
  single { AccountRepository() }
  single { AppRepository() }
  single { SavedAccessTokenRepository(get(), get()) }
  single { AccessTokenRepository(get()) }
  single { TimelinesRepository() }
}
