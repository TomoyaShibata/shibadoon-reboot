package com.tomoyashibata.shibadoon.model.repository

import com.tomoyashibata.shibadoon.model.data.App
import com.tomoyashibata.shibadoon.model.data.Authentication
import com.tomoyashibata.shibadoon.model.network.MastodonApi
import kotlinx.coroutines.experimental.launch
import timber.log.Timber
import kotlin.coroutines.experimental.suspendCoroutine

class AppRepository(
  private val mastodonApi: MastodonApi
) {
  suspend fun registerApp(app: App): Authentication? {
    return try {
      this@AppRepository.mastodonApi.registerApp(app).await()
    } catch (e: Exception) {
      Timber.e(e)
      null
    }
  }
}
