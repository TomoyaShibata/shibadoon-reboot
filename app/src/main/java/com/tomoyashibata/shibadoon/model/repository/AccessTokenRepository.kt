package com.tomoyashibata.shibadoon.model.repository

import com.tomoyashibata.shibadoon.database.AppDatabase
import com.tomoyashibata.shibadoon.model.data.RequestAccessToken
import com.tomoyashibata.shibadoon.model.data.AccessToken
import com.tomoyashibata.shibadoon.model.network.MastodonApi
import org.koin.core.parameter.ParameterList
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import timber.log.Timber

class AccessTokenRepository(
  private val database: AppDatabase
) : KoinComponent {
  suspend fun getAccessToken(instance: String, requestAccessToken: RequestAccessToken): AccessToken? {
    val mastodonApi: MastodonApi by inject { ParameterList(instance, "") }

    return try {
      mastodonApi.getToken(requestAccessToken).await()
    } catch (e: Exception) {
      null
    }
  }

  fun getAllTokens(): List<AccessToken> =
    this.database.tokenDao().getAll()

  fun saveToken(accessToken: AccessToken) {
    this.database.tokenDao().insert(accessToken)
    Timber.i("get: ${this.database.tokenDao().getAll()}")
  }
}
