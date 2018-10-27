package com.tomoyashibata.shibadoon.model.usecase

import com.tomoyashibata.shibadoon.model.data.PostStatus
import com.tomoyashibata.shibadoon.model.data.Status
import com.tomoyashibata.shibadoon.model.repository.StatusesRepository
import kotlin.coroutines.experimental.suspendCoroutine

class CreateTootUseCase(
  private val statusesRepository: StatusesRepository
) {
  suspend fun execute(statusText: String): Status = suspendCoroutine { cont ->
    val postStatus = PostStatus(status = statusText)
    val status = this.statusesRepository.postStatus(postStatus)
    cont.resume(status)
  }
}
