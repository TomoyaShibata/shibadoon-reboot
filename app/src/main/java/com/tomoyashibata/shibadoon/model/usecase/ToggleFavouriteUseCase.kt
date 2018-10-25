package com.tomoyashibata.shibadoon.model.usecase

import com.tomoyashibata.shibadoon.model.data.Status
import com.tomoyashibata.shibadoon.model.repository.StatusesRepository
import kotlin.coroutines.experimental.suspendCoroutine

class ToggleFavouriteUseCase(
  private val statusesRepository: StatusesRepository
) {
  suspend fun execute(status: Status): Status = suspendCoroutine { cont ->
    val targetStatus = status.reblog ?: status
    val isReblogStatus = status.reblog != null

    val newStatus = if (targetStatus.favourited == true) {
      this.statusesRepository.unfavourite(targetStatus.id)
    } else {
      this.statusesRepository.favourite(targetStatus.id)
    }

    if (!isReblogStatus) {
      cont.resume(newStatus)
      return@suspendCoroutine
    }

    cont.resume(this.statusesRepository.fetchStatus(status.id))
  }
}
