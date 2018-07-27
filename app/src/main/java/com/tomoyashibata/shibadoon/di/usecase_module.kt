package com.tomoyashibata.shibadoon.di

import com.tomoyashibata.shibadoon.model.usecase.GetTokenUseCase
import com.tomoyashibata.shibadoon.model.usecase.HasSavedTokenUseCase
import com.tomoyashibata.shibadoon.model.usecase.LoginUseCase
import com.tomoyashibata.shibadoon.model.usecase.SaveTokenUseCase
import org.koin.dsl.module.module

val useCaseModule = module {
  single { GetTokenUseCase(get()) }
  single { HasSavedTokenUseCase(get()) }
  single { LoginUseCase(get()) }
  single { SaveTokenUseCase(get()) }
}
