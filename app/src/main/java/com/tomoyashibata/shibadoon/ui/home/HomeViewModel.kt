package com.tomoyashibata.shibadoon.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomoyashibata.shibadoon.model.data.Account
import com.tomoyashibata.shibadoon.model.usecase.ChangeCurrentSavedAccountUseCase
import com.tomoyashibata.shibadoon.model.usecase.GetCurrentSavedAccountUseCase
import com.tomoyashibata.shibadoon.model.usecase.GetSavedAccountsUseCase
import com.tomoyashibata.shibadoon.model.usecase.HasSavedTokenUseCase
import com.tomoyashibata.shibadoon.ui.SingleLiveEvent
import com.tomoyashibata.shibadoon.ui.async
import com.tomoyashibata.shibadoon.ui.ui

class HomeViewModel(
  private val changeCurrentSavedAccountUseCase: ChangeCurrentSavedAccountUseCase,
  private val getCurrentSavedAccountUseCase: GetCurrentSavedAccountUseCase,
  private val getSavedAccountsUseCase: GetSavedAccountsUseCase,
  private val hasSavedTokenUseCase: HasSavedTokenUseCase
) : ViewModel() {
  val onRequestNavigateToLoginFragmentEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
  var currentSavedAccount: MutableLiveData<Account> = MutableLiveData()
  val savedAccounts: MutableLiveData<List<Pair<Long, Account>>> = MutableLiveData()

  init {
    ui {
      val hasSavedToken = async { this@HomeViewModel.hasSavedTokenUseCase.execute() }.await()
      if (!hasSavedToken) {
        this@HomeViewModel.onRequestNavigateToLoginFragmentEvent.call()
        return@ui
      }

      this@HomeViewModel.getAccounts()
    }
  }

  val postGetAccountsEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
  private fun getAccounts() {
    ui {
      this@HomeViewModel.currentSavedAccount.value = async { this@HomeViewModel.getCurrentSavedAccountUseCase.execute() }.await()
      this@HomeViewModel.savedAccounts.value = async { this@HomeViewModel.getSavedAccountsUseCase.execute() }.await()
      this@HomeViewModel.postGetAccountsEvent.call()
    }
  }

  val postChangeAccountEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
  fun changeCurrentSaved(accessTokenId: Long) {
    ui {
      async { this@HomeViewModel.changeCurrentSavedAccountUseCase.execute(accessTokenId) }.await()
      this@HomeViewModel.postChangeAccountEvent.call()
    }
  }

  val onClickCreateTootEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
  fun onClickCreateTootButton() {
    this.onClickCreateTootEvent.call()
  }
}
