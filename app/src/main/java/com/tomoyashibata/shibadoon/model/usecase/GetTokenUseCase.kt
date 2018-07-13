package com.tomoyashibata.shibadoon.model.usecase

import com.tomoyashibata.shibadoon.model.data.Authentication
import com.tomoyashibata.shibadoon.model.data.RequestToken
import com.tomoyashibata.shibadoon.model.repository.TokenRepository
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.suspendCoroutine

class GetTokenUseCase(
  private val tokenRepository: TokenRepository
) {
  suspend fun execute(authentication: Authentication, code: String) = suspendCoroutine<Unit> { cont ->
    val requestToken = RequestToken(
      clientId = authentication.clientId,
      clientSecret = authentication.clientSecret,
      code = code
    )

    launch(cont.context) {
      try {
        this@GetTokenUseCase.tokenRepository.getToken(requestToken)
        cont.resume(Unit)
      } catch (e: Exception) {
        cont.resumeWithException(e)
      }
    }
  }
}
