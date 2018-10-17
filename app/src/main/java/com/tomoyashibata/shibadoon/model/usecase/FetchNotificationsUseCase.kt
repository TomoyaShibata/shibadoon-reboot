package com.tomoyashibata.shibadoon.model.usecase

import com.tomoyashibata.shibadoon.model.data.Notification
import com.tomoyashibata.shibadoon.model.repository.NotificationsRepository
import kotlin.coroutines.experimental.suspendCoroutine

class FetchNotificationsUseCase(
  private val notificationsRepository: NotificationsRepository
) {
  suspend fun execute() = suspendCoroutine<List<Notification>> { cont ->
    val result = this.notificationsRepository.fetchNotifications()
    cont.resume(result)
  }
}
