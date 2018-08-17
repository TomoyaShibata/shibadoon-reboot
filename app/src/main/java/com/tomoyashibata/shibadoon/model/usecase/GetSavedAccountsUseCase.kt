package com.tomoyashibata.shibadoon.model.usecase

import com.tomoyashibata.shibadoon.model.data.Account
import com.tomoyashibata.shibadoon.model.repository.AccountRepository
import com.tomoyashibata.shibadoon.model.repository.SavedAccessTokenRepository
import kotlin.coroutines.experimental.suspendCoroutine

class GetSavedAccountsUseCase(
  private val accountRepository: AccountRepository,
  private val savedAccessTokenRepository: SavedAccessTokenRepository
) {
  suspend fun execute(): List<Pair<Long, Account>> = suspendCoroutine { cont ->
    val savedAccessTokens = this.savedAccessTokenRepository.getSavedAccessToken()
    val accountsWithAccessTokenId = savedAccessTokens.map {
      val account = this.accountRepository.getCurrentAccount(it.instance, it.accessToken)
      Pair(it.id, account)
    }

    cont.resume(accountsWithAccessTokenId)
  }
}
