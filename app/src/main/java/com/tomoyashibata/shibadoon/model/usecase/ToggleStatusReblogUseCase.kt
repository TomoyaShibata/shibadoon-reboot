package com.tomoyashibata.shibadoon.model.usecase

import com.tomoyashibata.shibadoon.model.data.Status
import com.tomoyashibata.shibadoon.model.repository.StatusesRepository
import kotlin.coroutines.experimental.suspendCoroutine

class ToggleStatusReblogUseCase(
  private val statusesRepository: StatusesRepository
) {
  suspend fun execute(status: Status): Status = suspendCoroutine { cont ->
    val targetStatus = status.reblog ?: status

    val newStatus = if (targetStatus.reblogged == true) {
      this.statusesRepository.unreblog(status.id)
    } else {
      this.statusesRepository.reblog(status.id)
    }

    cont.resume(newStatus)
  }
}
