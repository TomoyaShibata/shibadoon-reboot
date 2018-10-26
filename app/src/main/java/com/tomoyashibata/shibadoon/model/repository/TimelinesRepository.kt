package com.tomoyashibata.shibadoon.model.repository

import com.tomoyashibata.shibadoon.model.data.Status
import com.tomoyashibata.shibadoon.model.network.MastodonApi
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class TimelinesRepository : KoinComponent {
  private val service: MastodonApi by inject(name = "currentAccount")

  fun fetchHomeTimeline(): List<Status> {
    val result = this.service.fetchHomeTimeline().execute()
    return result.body() ?: throw Exception(result.errorBody()?.string())
  }

  fun fetchHomeTimeline(maxId: Long): List<Status> {
    val result = this.service.fetchHomeTimeline(maxId = maxId).execute()
    return result.body() ?: throw Exception(result.errorBody()?.string())
  }
}
