package com.tomoyashibata.shibadoon.ui.hometimeline

import androidx.lifecycle.ViewModel
import com.here.oksse.OkSse
import com.here.oksse.ServerSentEvent
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tomoyashibata.shibadoon.model.data.Status
import com.tomoyashibata.shibadoon.model.usecase.FetchHomeTimelineUseCase
import com.tomoyashibata.shibadoon.model.usecase.FetchOldHomeTimelineUseCase
import com.tomoyashibata.shibadoon.ui.SingleLiveEvent
import com.tomoyashibata.shibadoon.ui.async
import com.tomoyashibata.shibadoon.ui.ui
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber

class HomeTimelineViewModel(
  private val fetchHomeTimelineUseCase: FetchHomeTimelineUseCase,
  private val fetchOldHomeTimelineUseCase: FetchOldHomeTimelineUseCase
) : ViewModel() {
  val statuses: ArrayList<Status> = arrayListOf()

  val requestScrollToLatestStatusEvent: SingleLiveEvent<Unit> = SingleLiveEvent()

  init {
    this.sseStart()
    this.fetchHomeTimeline()
  }

  private fun fetchHomeTimeline() {
    ui {
      this@HomeTimelineViewModel.also {
        it.statuses.addAll(async { it.fetchHomeTimelineUseCase.execute() }.await())
        it.onChangedStatusesEvent.call()
        it.requestScrollToLatestStatusEvent.call()
      }
    }
  }

  fun fetchOldHomeTimeline() {
    if (this.statuses.isEmpty()) return

    val maxId = this.statuses.last().id

    ui {
      val oldStatuses = async { this@HomeTimelineViewModel.fetchOldHomeTimelineUseCase.execute(maxId) }.await()
      this@HomeTimelineViewModel.statuses.addAll(oldStatuses)
      this@HomeTimelineViewModel.onChangedStatusesEvent.call()
    }
  }

  val onChangedStatusesEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
  private fun sseStart() {
    Timber.i("sseStart!!!")
    val jsonAdapter = Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .build()
      .adapter(Status::class.java)

    val request = Request.Builder()
      .header("Authorization", "Bearer 0122011c0c11d0c2905d3050042e0a0a8f39940e40b5f3d91a755bc78f311979")
      .url("https://m6n.onsen.tech/api/v1/streaming/user")
      .build()
    Timber.i("request!!!")


    val okSse = OkSse()
    Timber.i("okSse!!!")

    okSse.newServerSentEvent(request, object : ServerSentEvent.Listener {
      init {
        Timber.i("ServerSentEvent.Listener!!!")
      }

      override fun onOpen(sse: ServerSentEvent?, response: Response?) {
        Timber.i(response.toString())
      }

      override fun onRetryTime(sse: ServerSentEvent?, milliseconds: Long): Boolean = true

      override fun onComment(sse: ServerSentEvent?, comment: String?) {
      }

      override fun onRetryError(sse: ServerSentEvent?, throwable: Throwable?, response: Response?): Boolean = true

      override fun onPreRetry(sse: ServerSentEvent?, originalRequest: Request?): Request = originalRequest!!

      override fun onMessage(sse: ServerSentEvent?, id: String?, event: String?, message: String?) {
        if (message == null) return

        when (event) {
          "update" -> {
            try {
              val status = jsonAdapter.fromJson(message)
              status?.let {
                this@HomeTimelineViewModel.statuses.add(0, it)
                Timber.i("id: ${it.id}")
                this@HomeTimelineViewModel.onChangedStatusesEvent.postValue(Unit)
              }
            } catch (e: JsonDataException) {
              Timber.e("${e.message}: $message")
            }
          }
          "delete" -> {
            this@HomeTimelineViewModel.statuses.removeIf { it.id == message.toLong() }
            this@HomeTimelineViewModel.onChangedStatusesEvent.postValue(Unit)
          }
        }
      }

      override fun onClosed(sse: ServerSentEvent?) {
      }
    })
  }
}
