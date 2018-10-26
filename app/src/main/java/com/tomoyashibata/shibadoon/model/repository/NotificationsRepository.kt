package com.tomoyashibata.shibadoon.model.repository

import com.tomoyashibata.shibadoon.model.data.Notification
import com.tomoyashibata.shibadoon.model.network.MastodonApi
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class NotificationsRepository : KoinComponent {
  private val service: MastodonApi by inject(name = "currentAccount")

  fun fetchNotifications(): List<Notification> {
    val result = this.service.fetchNotification().execute()
    return result.body() ?: throw Exception(result.errorBody()?.string())
  }

  fun fetchNotifications(maxId: Long): List<Notification> {
    val result = this.service.fetchNotification(maxId = maxId).execute()
    return result.body() ?: throw Exception(result.errorBody()?.string())
  }
}
