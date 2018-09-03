package com.tomoyashibata.shibadoon.model.usecase

import com.tomoyashibata.shibadoon.model.data.Status
import com.tomoyashibata.shibadoon.model.repository.TimelinesRepository
import kotlin.coroutines.experimental.suspendCoroutine

class FetchOldHomeTimelineUseCase(
  private val timelinesRepository: TimelinesRepository
) {
  suspend fun execute(maxId: Long) = suspendCoroutine<List<Status>> { cont ->
    val result = this.timelinesRepository.fetchHomeTimeline(maxId)
    cont.resume(result)
  }
}
