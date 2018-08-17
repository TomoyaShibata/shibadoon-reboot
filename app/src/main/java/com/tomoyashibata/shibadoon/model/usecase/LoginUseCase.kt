package com.tomoyashibata.shibadoon.model.usecase

import com.tomoyashibata.shibadoon.model.data.App
import com.tomoyashibata.shibadoon.model.data.Authentication
import com.tomoyashibata.shibadoon.model.repository.AppRepository
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.suspendCoroutine

class LoginUseCase(
  private val appRepository: AppRepository
) {
  suspend fun execute(instance: String) = suspendCoroutine<Authentication> { cont ->
    val app = App()
    launch(cont.context) {
      try {
        val authentication = this@LoginUseCase.appRepository.registerApp(instance, app)
        if (authentication == null) {
          cont.resumeWithException(Exception("custom"))
          return@launch
        }

        cont.resume(authentication)
      } catch (e: Exception) {
        cont.resumeWithException(e)
      }
    }

    //UnknownHostException
  }
}
