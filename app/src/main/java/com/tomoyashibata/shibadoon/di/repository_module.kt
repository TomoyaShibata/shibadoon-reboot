package com.tomoyashibata.shibadoon.di

import com.tomoyashibata.shibadoon.model.repository.AccountRepository
import com.tomoyashibata.shibadoon.model.repository.AppRepository
import com.tomoyashibata.shibadoon.model.repository.TokenRepository
import com.tomoyashibata.shibadoon.model.repository.ValidAccountTokenRepository
import org.koin.dsl.module.module

val repositoryModule = module {
  single { AccountRepository(get()) }
  single { AppRepository() }
  single { TokenRepository(get()) }
  single { ValidAccountTokenRepository(get()) }
}
