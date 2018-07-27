package com.tomoyashibata.shibadoon.model.usecase

import com.tomoyashibata.shibadoon.model.data.Authentication
import com.tomoyashibata.shibadoon.model.data.RequestToken
import com.tomoyashibata.shibadoon.model.repository.TokenRepository
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.suspendCoroutine

class GetTokenUseCase(
  private val tokenRepository: TokenRepository
) {
  suspend fun execute(instance: String, authentication: Authentication, code: String) = suspendCoroutine<Unit> { cont ->
    val requestToken = RequestToken(
      clientId = authentication.clientId,
      clientSecret = authentication.clientSecret,
      code = code
    )

    launch(cont.context) {
      try {
        val token = this@GetTokenUseCase.tokenRepository.getToken(instance, requestToken)
        token?.let { this@GetTokenUseCase.tokenRepository.saveToken(it) }
        cont.resume(Unit)
      } catch (e: Exception) {
        cont.resumeWithException(e)
      }
    }
  }
}
