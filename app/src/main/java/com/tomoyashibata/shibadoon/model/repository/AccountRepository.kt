package com.tomoyashibata.shibadoon.model.repository

import com.tomoyashibata.shibadoon.model.data.Account
import com.tomoyashibata.shibadoon.model.network.MastodonApi
import org.koin.core.parameter.ParameterList
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class AccountRepository : KoinComponent {
  fun getCurrentAccount(instance: String, accessToken: String): Account {
    val service: MastodonApi by inject("withParams") { ParameterList(instance, accessToken) }
    val result = service.getCurrentAccount().execute()
    return result.body() ?: throw Exception(result.errorBody()?.string())
  }
}
