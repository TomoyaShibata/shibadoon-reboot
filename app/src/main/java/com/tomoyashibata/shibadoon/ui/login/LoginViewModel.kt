package com.tomoyashibata.shibadoon.ui.login

import android.arch.lifecycle.ViewModel
import com.tomoyashibata.shibadoon.model.data.Authentication
import com.tomoyashibata.shibadoon.model.usecase.GetTokenUseCase
import com.tomoyashibata.shibadoon.model.usecase.LoginUseCase
import com.tomoyashibata.shibadoon.ui.SingleLiveEvent
import com.tomoyashibata.shibadoon.ui.async
import com.tomoyashibata.shibadoon.ui.ui

class LoginViewModel(
  private val getTokenUseCase: GetTokenUseCase,
  private val loginUseCase: LoginUseCase
) : ViewModel() {
  //private val authentication: Authentication? = null
  private var authentication: Authentication? = null

  val onRegisterAppEvent: SingleLiveEvent<Authentication> = SingleLiveEvent()
  fun onLoginClick() {
    ui {
      this@LoginViewModel.authentication = async { this@LoginViewModel.loginUseCase.execute("") }.await()
      this@LoginViewModel.onRegisterAppEvent.apply {
        value = authentication
        call()
      }
    }
  }

  fun onSuccessGetCode(code: String) {
    if (this.authentication == null) {
      return
    }

    async {
      authentication?.let { this@LoginViewModel.getTokenUseCase.execute(it, code) }
    }
  }
}
