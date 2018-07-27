package com.tomoyashibata.shibadoon.model.usecase

import com.tomoyashibata.shibadoon.model.data.ValidAccountToken
import com.tomoyashibata.shibadoon.model.repository.ValidAccountTokenRepository
import timber.log.Timber
import kotlin.coroutines.experimental.suspendCoroutine

class SaveTokenUseCase(
  private val validAccountTokenRepository: ValidAccountTokenRepository
) {
  suspend fun execute() = suspendCoroutine<Unit> { cont ->
    val validAccountToken = ValidAccountToken(
      instance = "",
      accountId = "",
      accessToken = "",
      isCurrent = true
    )

    val result = this.validAccountTokenRepository.getAll()
    Timber.i("result: $result")

    cont.resume(Unit)
  }
}
