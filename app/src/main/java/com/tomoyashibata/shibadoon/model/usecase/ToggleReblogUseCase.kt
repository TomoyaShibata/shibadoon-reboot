package com.tomoyashibata.shibadoon.model.usecase

import com.tomoyashibata.shibadoon.model.data.Status
import com.tomoyashibata.shibadoon.model.repository.StatusesRepository
import kotlin.coroutines.experimental.suspendCoroutine

class ToggleReblogUseCase(
  private val statusesRepository: StatusesRepository
) {
  suspend fun execute(status: Status): Status = suspendCoroutine { cont ->
    val targetStatus = status.reblog ?: status
    val isReblogStatus = status.reblog != null

    val newStatus = if (targetStatus.reblogged == true) {
      this.statusesRepository.unreblog(targetStatus.id)
    } else {
      this.statusesRepository.reblog(targetStatus.id)
    }

    if (!isReblogStatus) {
      cont.resume(newStatus)
      return@suspendCoroutine
    }

    cont.resume(this.statusesRepository.fetchStatus(status.id))
  }
}
