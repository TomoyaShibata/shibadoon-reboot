package com.tomoyashibata.shibadoon.model.repository

import com.tomoyashibata.shibadoon.model.data.RequestToken
import com.tomoyashibata.shibadoon.model.data.Token
import com.tomoyashibata.shibadoon.model.network.MastodonApi
import timber.log.Timber

class TokenRepository(
  private val mastodonApi: MastodonApi
) {
  suspend fun getToken(requestToken: RequestToken): Token? {
    return try {
      Timber.i("おけおけおっけー")
      val token = this@TokenRepository.mastodonApi.getToken(requestToken).await()
      Timber.i(token.toString())
      token
    } catch (e: Exception) {
      Timber.e(e)
      null
    }
  }
}
