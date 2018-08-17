package com.tomoyashibata.shibadoon.ui.login

import android.arch.lifecycle.MutableLiveData
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
  val instance: MutableLiveData<String> = MutableLiveData()
  private var authentication: Authentication? = null

  val instanceBlankErrorEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
  val onRegisterAppEvent: SingleLiveEvent<Authentication> = SingleLiveEvent()
  fun onLoginClick() {
    if (this.instance.value.isNullOrBlank()) {
      this.instanceBlankErrorEvent.call()
      return
    }

    ui {
      this@LoginViewModel.authentication = async { this@LoginViewModel.instance.value?.let { this@LoginViewModel.loginUseCase.execute(it) } }.await()
      this@LoginViewModel.onRegisterAppEvent.apply {
        value = this@LoginViewModel.authentication
      }
    }
  }

  val onLoginFinishEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
  fun onSuccessGetCode(code: String) {
    val instance = this.instance.value ?: return
    val authentication = this@LoginViewModel.authentication ?: return

    ui {
      async { this@LoginViewModel.getTokenUseCase.execute(instance, authentication, code) }.await()
      this@LoginViewModel.onLoginFinishEvent.call()
    }
  }
}
