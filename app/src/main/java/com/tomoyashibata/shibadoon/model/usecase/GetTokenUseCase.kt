package com.tomoyashibata.shibadoon.model.usecase

import com.tomoyashibata.shibadoon.model.data.Authentication
import com.tomoyashibata.shibadoon.model.data.RequestAccessToken
import com.tomoyashibata.shibadoon.model.data.SavedAccessToken
import com.tomoyashibata.shibadoon.model.repository.SavedAccessTokenRepository
import com.tomoyashibata.shibadoon.model.repository.AccessTokenRepository
import kotlinx.coroutines.experimental.launch
import timber.log.Timber
import kotlin.coroutines.experimental.suspendCoroutine

class GetTokenUseCase(
  private val savedAccessTokenRepository: SavedAccessTokenRepository,
  private val accessTokenRepository: AccessTokenRepository
) {
  suspend fun execute(instance: String, authentication: Authentication, code: String) = suspendCoroutine<Unit> { cont ->
    val requestAccessToken = RequestAccessToken(
      clientId = authentication.clientId,
      clientSecret = authentication.clientSecret,
      code = code
    )

    launch(cont.context) {
      try {
        val accessToken = this@GetTokenUseCase.accessTokenRepository.getAccessToken(instance, requestAccessToken)
        accessToken?.let {
          val savedAccount = SavedAccessToken(accessToken = it.accessToken, instance = instance)
          this@GetTokenUseCase.savedAccessTokenRepository.addSavedAccessToken(savedAccount)
        }
        cont.resume(Unit)
      } catch (e: Exception) {
        Timber.e(e)
        cont.resumeWithException(e)
      }
    }
  }
}
