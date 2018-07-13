package com.tomoyashibata.shibadoon.model.repository

import com.tomoyashibata.shibadoon.model.data.Account
import com.tomoyashibata.shibadoon.model.network.MastodonApi

class AccountRepository(
  private val mastodonApi: MastodonApi
) {
  fun fetchAccount(id: String): Account {
    return this.mastodonApi.fetchAccount(id).execute().body()
      ?: throw Exception("")
  }

  fun getCurrentUser(): Account {
    return mastodonApi.getCurrentUser().execute().body()
      ?: throw Exception("")
  }
}
