package com.tomoyashibata.shibadoon.model.usecase

import com.tomoyashibata.shibadoon.model.data.Status
import com.tomoyashibata.shibadoon.model.repository.TimelinesRepository
import kotlin.coroutines.experimental.suspendCoroutine

class FetchHomeTimelineUseCase(
  private val timelinesRepository: TimelinesRepository
) {
  suspend fun execute() = suspendCoroutine<List<Status>> { cont ->
    val result = this.timelinesRepository.fetchHomeTimeline()
    cont.resume(result)
  }
}
