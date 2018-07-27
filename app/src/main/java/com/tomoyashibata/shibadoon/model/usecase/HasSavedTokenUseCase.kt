package com.tomoyashibata.shibadoon.model.usecase

import com.tomoyashibata.shibadoon.model.repository.TokenRepository
import kotlin.coroutines.experimental.suspendCoroutine

class HasSavedTokenUseCase(
  private val tokenRepository: TokenRepository
) {
  suspend fun execute(): Boolean = suspendCoroutine { cont ->
    val tokens = this.tokenRepository.getAllTokens()
    val hasSavedToken = tokens.isNotEmpty()
    cont.resume(hasSavedToken)
  }
}
