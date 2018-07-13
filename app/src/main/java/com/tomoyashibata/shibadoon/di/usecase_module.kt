package com.tomoyashibata.shibadoon.di

import com.tomoyashibata.shibadoon.model.usecase.GetTokenUseCase
import com.tomoyashibata.shibadoon.model.usecase.LoginUseCase
import org.koin.dsl.module.module

val useCaseModule = module {
  single { GetTokenUseCase(get()) }
  single { LoginUseCase(get()) }
}
