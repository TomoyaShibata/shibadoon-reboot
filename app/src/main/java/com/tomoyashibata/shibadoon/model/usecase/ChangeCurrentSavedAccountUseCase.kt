package com.tomoyashibata.shibadoon.model.usecase

import com.tomoyashibata.shibadoon.model.repository.SavedAccessTokenRepository
import kotlin.coroutines.experimental.suspendCoroutine

class ChangeCurrentSavedAccountUseCase(
  private val savedAccessTokenRepository: SavedAccessTokenRepository
) {
  suspend fun execute(accessTokenId: Long): Unit = suspendCoroutine { cont ->
    this.savedAccessTokenRepository.changeCurrentSavedAccessTokenId(accessTokenId)
    cont.resume(Unit)
  }
}
