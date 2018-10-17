package com.tomoyashibata.shibadoon.ui.notifications

import androidx.lifecycle.ViewModel
import com.tomoyashibata.shibadoon.model.data.Notification
import com.tomoyashibata.shibadoon.model.usecase.FetchNotificationsUseCase
import com.tomoyashibata.shibadoon.ui.SingleLiveEvent
import com.tomoyashibata.shibadoon.ui.async
import com.tomoyashibata.shibadoon.ui.ui

class NotificationsViewModel(
  private val fetchNotificationsUseCase: FetchNotificationsUseCase
) : ViewModel() {
  val notifications: ArrayList<Notification> = arrayListOf()

  init {
    this.fetchNotifications()
  }

  val onChangeNotificationsEvent: SingleLiveEvent<Unit> = SingleLiveEvent()

  fun fetchNotifications() {
    ui {
      this@NotificationsViewModel.also {
        it.notifications.addAll(async { it.fetchNotificationsUseCase.execute() }.await())
        it.onChangeNotificationsEvent.call()
      }
    }
  }
}
