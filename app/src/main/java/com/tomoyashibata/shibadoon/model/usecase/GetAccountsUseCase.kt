package com.tomoyashibata.shibadoon.model.usecase

import com.tomoyashibata.shibadoon.model.repository.AccessTokenRepository
import kotlin.coroutines.experimental.suspendCoroutine

class GetAccountsUseCase(
  private val accessTokenRepository: AccessTokenRepository
) {
  suspend fun execute(): Unit = suspendCoroutine { cont ->
    val tokens = this.accessTokenRepository.getAllTokens()



    cont.resume(Unit)
  }
}
