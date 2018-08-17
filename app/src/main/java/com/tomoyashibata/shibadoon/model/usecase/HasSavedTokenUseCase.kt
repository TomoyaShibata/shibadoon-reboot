package com.tomoyashibata.shibadoon.model.usecase

import com.tomoyashibata.shibadoon.model.repository.SavedAccessTokenRepository
import kotlin.coroutines.experimental.suspendCoroutine

class HasSavedTokenUseCase(
  private val tokenRepository: SavedAccessTokenRepository
) {
  suspend fun execute(): Boolean = suspendCoroutine { cont ->
    val savedAccessTokens = this.tokenRepository.getSavedAccessToken()
    val hasSavedToken = savedAccessTokens.isNotEmpty()
    cont.resume(hasSavedToken)
  }
}
