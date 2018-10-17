package com.tomoyashibata.shibadoon.ui.notifications

import com.airbnb.epoxy.EpoxyController
import com.tomoyashibata.shibadoon.itemFavoritedNotification
import com.tomoyashibata.shibadoon.itemReblogedNotification
import com.tomoyashibata.shibadoon.model.data.Notification
import com.tomoyashibata.shibadoon.model.data.NotificationType

class NotificationsController(
  private val data: ArrayList<Notification>,
  private val loadingMore: Boolean = false
) : EpoxyController() {
  override fun buildModels() {
    this.data.forEach {
      when (it.type) {
        NotificationType.reblog -> {
          itemReblogedNotification {
            id(it.id)
            notification(it)
          }
        }
        NotificationType.favourite -> {
          itemFavoritedNotification {
            id(it.id)
            notification(it)
          }
        }
        NotificationType.follow -> {
        }
        NotificationType.mention -> {
        }
      }
    }
  }
}
