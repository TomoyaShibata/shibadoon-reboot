package com.tomoyashibata.shibadoon.di

import com.tomoyashibata.shibadoon.model.usecase.*
import org.koin.dsl.module.module

val useCaseModule = module {
  single { ChangeCurrentSavedAccountUseCase(get()) }
  single { CreateTootUseCase(get()) }
  single { FetchHomeTimelineUseCase(get()) }
  single { FetchOldHomeTimelineUseCase(get()) }
  single { GetAccountsUseCase(get()) }
  single { GetCurrentSavedAccountUseCase(get(), get()) }
  single { GetSavedAccountsUseCase(get(), get()) }
  single { GetTokenUseCase(get(), get()) }
  single { HasSavedTokenUseCase(get()) }
  single { LoginUseCase(get()) }
  single { ToggleStatusReblogUseCase(get()) }
}
