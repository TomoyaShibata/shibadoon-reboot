package com.tomoyashibata.shibadoon.model.usecase

import com.tomoyashibata.shibadoon.model.data.Status
import com.tomoyashibata.shibadoon.model.repository.StatusesRepository
import kotlin.coroutines.experimental.suspendCoroutine

class FetchStatusUseCase(
  private val statusesRepository: StatusesRepository
) {
  suspend fun execute(id: Long) = suspendCoroutine<Status> { cont ->
    val status = this.statusesRepository.fetchStatus(id)
    cont.resume(status)
  }
}
