package com.tomoyashibata.shibadoon.di

import com.tomoyashibata.shibadoon.ui.home.HomeViewModel
import com.tomoyashibata.shibadoon.ui.login.LoginViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val shibadoonModule = module {
  viewModel { HomeViewModel(get()) }
  viewModel { LoginViewModel(get(), get()) }
}
