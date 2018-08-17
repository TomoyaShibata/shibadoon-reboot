package com.tomoyashibata.shibadoon.model.repository

import com.tomoyashibata.shibadoon.model.data.App
import com.tomoyashibata.shibadoon.model.data.Authentication
import com.tomoyashibata.shibadoon.model.network.MastodonApi
import org.koin.core.parameter.parametersOf
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import timber.log.Timber

class AppRepository : KoinComponent {
  suspend fun registerApp(instance: String, app: App): Authentication? {
    val mastodonApi: MastodonApi by inject { parametersOf(instance, "") }

    return try {
      mastodonApi.registerApp(app).await()
    } catch (e: Exception) {
      Timber.e(e)
      null
    }
  }
}
