package com.tomoyashibata.shibadoon.model.usecase

import okhttp3.Request
import kotlin.coroutines.experimental.suspendCoroutine

class GetStreamingUserRequestUseCase(
  private val streamingUserRequest: Request
) {
  suspend fun execute(): Request = suspendCoroutine { cont ->
    cont.resume(streamingUserRequest)
  }
}
