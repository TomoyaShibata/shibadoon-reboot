package com.tomoyashibata.shibadoon.model.repository

import com.tomoyashibata.shibadoon.database.AppDatabase
import com.tomoyashibata.shibadoon.model.data.RequestToken
import com.tomoyashibata.shibadoon.model.data.Token
import com.tomoyashibata.shibadoon.model.network.MastodonApi
import org.koin.core.parameter.ParameterList
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import timber.log.Timber

class TokenRepository(
  private val database: AppDatabase
) : KoinComponent {
  suspend fun getToken(instance: String, requestToken: RequestToken): Token? {
    val mastodonApi: MastodonApi by inject { ParameterList(instance) }

    return try {
      mastodonApi.getToken(requestToken).await()
    } catch (e: Exception) {
      Timber.e(e)
      null
    }
  }

  fun getAllTokens(): List<Token> =
    this.database.tokenDao().getAll()

  fun saveToken(token: Token) {
    this.database.tokenDao().insert(token)
    Timber.i("get: ${this.database.tokenDao().getAll()}")
  }
}
