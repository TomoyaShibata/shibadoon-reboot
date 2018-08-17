package com.tomoyashibata.shibadoon.model.usecase

import com.tomoyashibata.shibadoon.model.data.Account
import com.tomoyashibata.shibadoon.model.repository.AccountRepository
import com.tomoyashibata.shibadoon.model.repository.SavedAccessTokenRepository
import kotlin.coroutines.experimental.suspendCoroutine

class GetCurrentSavedAccountUseCase(
  private val accountRepository: AccountRepository,
  private val savedAccessTokenRepository: SavedAccessTokenRepository
) {
  suspend fun execute(): Account = suspendCoroutine { cont ->
    val currentSavedAccount = this.savedAccessTokenRepository.getCurrentSavedAccessToken()
    val account = this.accountRepository.getCurrentAccount(currentSavedAccount.instance, currentSavedAccount.accessToken)

    cont.resume(account)
  }
}
